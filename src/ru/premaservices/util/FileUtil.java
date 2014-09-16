package ru.premaservices.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import net.coobird.thumbnailator.Thumbnails;

public final class FileUtil {
	
	public static final int RESIZE_ALL = 0;
	public static final int RESIZE_ONLY_MINIMIZE = 1;
	public static final int RESIZE_ONLY_MAXIMIZE = 2;
	
	public final static byte[] resizeImage (byte[] img, int w, int t) throws IOException {
		
		ByteArrayInputStream is = new ByteArrayInputStream(img);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		BufferedImage originalImage = ImageIO.read(is);
		switch (t) {
		case RESIZE_ONLY_MINIMIZE: 
			if (originalImage.getWidth() <= w) return img;
			break;
		case RESIZE_ONLY_MAXIMIZE:
			if (originalImage.getWidth() >= w) return img;
			break;
		case RESIZE_ALL:
			if (originalImage.getWidth() == w) return img;
		}
		int h = w*originalImage.getWidth()/originalImage.getHeight();
	
		Thumbnails.of(originalImage).size(w, h).outputFormat("png").toOutputStream(os);
		return os.toByteArray();
	}
	
	public final static byte[] getImage (InputStream is) throws IOException {
		if (is == null) return null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BufferedImage originalImage = ImageIO.read(is);
		ImageIO.write(originalImage, "png", os);
		return os.toByteArray();
	}
	
	public final static void saveXml (MultipartFile file, Resource tResource, Resource sResource, String filename, Properties msg) throws Exception {
		
		  try {	
			  DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			  Document doc = builder.parse(new InputSource(file.getInputStream()));
			  
			  addXmlAttrs(doc.getDocumentElement(), msg);
			  
			  DOMSource source = new DOMSource(doc);
			  
			  InputStream schema = sResource.getInputStream();
			  SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);	
			  
			  Validator validator = factory.newSchema(new StreamSource(schema)).newValidator();	  
			  validator.setErrorHandler(new ErrorHandler () {
				  public void warning(SAXParseException exception) {
					  Logger.getLogger(this.getClass()).warn("FileUtil.saveXml - WARNING: " + exception.toString());	        
			      }
	
			      public void error(SAXParseException exception) {
			    	  Logger.getLogger(this.getClass()).error("FileUtil.saveXml - ERROR: " + exception.toString());	         
			      }
	
			      public void fatalError(SAXParseException exception) {
			    	  Logger.getLogger(this.getClass()).fatal("FileUtil.saveXml - FATAL: " + exception.toString());			        
			      }		        
			  });	
			  validator.validate(source, new DOMResult());
			  
			  file.transferTo(new File(filename));  
			  transformXml(source, tResource, filename);
		  }
		  catch (Exception err) {			
			  Logger.getLogger(new FileUtil().getClass()).error("FileUtil.saveXml - ERROR: " + err.toString());	 
			  err.printStackTrace();
			  throw err;
		  }	  	
		    
	}
	
	public final static void createXml (Resource tResource, String filename, Properties msg) throws Exception {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("note");
		    doc.appendChild(root);
		    addXmlAttrs(root, msg);
		    transformXml(new DOMSource(doc), tResource, filename);
		}
		catch (Exception e) {
			Logger.getLogger(new FileUtil().getClass()).error("FileUtil.createXml - ERROR: " + e.toString());	
			e.printStackTrace();
			throw e;
		}
	}
	
	public final static void removeXml (String filename) throws Exception {
		Exception err = null;
		try {
			File file = new File(filename + ".html");
			file.delete();
		}
		catch (Exception e) {
			Logger.getLogger(new FileUtil().getClass()).error("FileUtil.removeXml - ERROR: " + e.toString());	
			e.printStackTrace();
			err = e;
		}
		try {
			File file = new File(filename);
			file.delete();
		}
		catch (Exception e) {
			Logger.getLogger(new FileUtil().getClass()).error("FileUtil.removeXml - ERROR: " + e.toString());	
			e.printStackTrace();
			throw e;
		}
		if (err != null) {
			throw err;
		}
	}
	
	public final static void parseXml (Resource fResource, Resource sResource, DefaultHandler handler) throws Exception {
		
		InputStream schema = sResource.getInputStream();
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);	
		
		Validator validator = factory.newSchema(new StreamSource(schema)).newValidator();	  
		  validator.setErrorHandler(new ErrorHandler () {
			  public void warning(SAXParseException exception) {
				  Logger.getLogger(this.getClass()).warn("FileUtil.saveXml - WARNING: " + exception.toString());	        
		      }

		      public void error(SAXParseException exception) {
		    	  Logger.getLogger(this.getClass()).error("FileUtil.saveXml - ERROR: " + exception.toString());	         
		      }

		      public void fatalError(SAXParseException exception) {
		    	  Logger.getLogger(this.getClass()).fatal("FileUtil.saveXml - FATAL: " + exception.toString());			        
		      }		        
		  });	
		  
		  SAXResult result = new SAXResult(handler);
		  validator.validate(new SAXSource(new InputSource(fResource.getInputStream())), result);
		
	}
	
	public final static void transformXml (Source source, Resource tResource, String filename) throws Exception {
		
		File file = new File(filename + ".html");
		file.getParentFile().mkdirs();
		FileOutputStream out = new FileOutputStream(file);	
			  
		StreamSource xsl = new StreamSource(tResource.getInputStream()); 	
		Templates template = TransformerFactory.newInstance().newTemplates(xsl);    
		Transformer transformer = template.newTransformer();
		  
		transformer.transform(source, new StreamResult(out));
		out.flush();
		out.close();
	
	}
	
	final static void addXmlAttrs (Element root, Properties msg) {
		Enumeration<?> keys = msg.propertyNames();
		while (keys.hasMoreElements()) {
			String key = (String)keys.nextElement();
			int idx = key.lastIndexOf('.');
			if (idx > -1 && idx < key.length() - 1) {
				root.setAttribute(key.substring(idx + 1), msg.getProperty(key));
			}
			else {
				root.setAttribute(key, msg.getProperty(key));
			}
		}
	}

}
