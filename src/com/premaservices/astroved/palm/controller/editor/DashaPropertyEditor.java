package com.premaservices.astroved.palm.controller.editor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class DashaPropertyEditor extends PropertyEditorSupport {
	
	private static final String DELIMITER = " - ";
		
	@Override
	public String getAsText() {
		return getAsText((Integer)this.getValue());					
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {				
		if (StringUtils.isNotBlank(text)) {
			String value = StringUtils.replace(text, DELIMITER, "");
			this.setValue(Integer.parseInt(value));					
		}
	}
	
	public String getAsText (Integer value) {		
		String v = String.valueOf(value);		
		char[] charArray = v.toCharArray();
		Character[] charObjectArray = ArrayUtils.toObject(charArray);		
		return StringUtils.join( charObjectArray, DELIMITER);				
	}

}
