package com.premaservices.astroved.palm.controller.editor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;

public class EnumPropertyEditor<T extends Enum<T>> extends PropertyEditorSupport {
	
	private Class<T> clazz;
	
	public EnumPropertyEditor(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getAsText() {
		T value = (T)this.getValue();
		return value.toString();		
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {				
		if (StringUtils.isNotBlank(text)) {
			this.setValue(Enum.valueOf(clazz, text.toUpperCase()));
		}
	}

}
