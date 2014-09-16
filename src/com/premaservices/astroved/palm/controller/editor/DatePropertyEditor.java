package com.premaservices.astroved.palm.controller.editor;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DatePropertyEditor extends PropertyEditorSupport {
	
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
	public static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("kk:mm");
	public static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	
	private DateFormat formatter;

	public DatePropertyEditor (DateFormat df) {
		formatter = df;
	}
	
	@Override
	public String getAsText() {
		Date date = (Date)getValue();
		if (date != null) {
			String text = formatter.format(date);
			return text;
		}
		else
			return null;
	}

	@Override
	public void setAsText(String text) {	
		if (StringUtils.isNotBlank(text)) {
			try {
				Date date = formatter.parse(text);
				setValue(date);
			}
			catch (ParseException e) {
				setValue(null);
			}
		}
	}

}
