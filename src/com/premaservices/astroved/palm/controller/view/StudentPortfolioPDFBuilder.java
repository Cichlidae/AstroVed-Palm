package com.premaservices.astroved.palm.controller.view;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class StudentPortfolioPDFBuilder extends AbstractPdfView {
	
	public static final String STUDENT_KEY = "studentPortfolio";
	
	public static final String PDF_1ST_COURSE_SOURCE_NAME = "1" + DIPLOMA_CODE_WORD;
	public static final String PDF_2ND_COURSE_SOURCE_NAME = "2" + DIPLOMA_CODE_WORD;
	public static final String PDF_3RD_COURSE_SOURCE_NAME = "3" + DIPLOMA_CODE_WORD;
	public static final String PDF_GR_COURSE_SOURCE_NAME = "4" + DIPLOMA_CODE_WORD;
	
	private static final Pattern DIPLOMA_LIST_PATTERN = Pattern.compile("*" + DIPLOMA_CODE_WORD);

	@Override
	protected void buildPdfDocument(Map<String, Object> map, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse responce) throws Exception {	
		
		Iterator<String>keys = map.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			Matcher m = DIPLOMA_LIST_PATTERN.matcher(key);
			if (m.matches()) {
				@SuppressWarnings("unchecked")
				List<Diploma> diplomas = (List<Diploma>)map.get(key);
				if (!diplomas.isEmpty()) {
					Course course = diplomas.get(0).getCourse();
					doc.add(new Paragraph(course.toString()));
					
					for (Diploma d : diplomas) {
						String stroke = d.getNumber() + ". " + d.getStudent().getFamily() + " " + d.getStudent().getName() + " " + d.getStudent().getPatronimic() + "(" +
								        d.getStudent().getCity().getCity() + ", " + d.getDiplomaDate().toString() + ")";
						doc.add(new Paragraph(stroke));
					}					
				}				
			}			
		}
		doc.addCreator("PremaServices.com");
		doc.addAuthor("*ASTRO-VED* SCHOOL");
		doc.addCreationDate();
		doc.addTitle("ASTRO-VED PALMS - student portfolio");		
	}

}
