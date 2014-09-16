package com.premaservices.astroved.palm.service;

import java.util.Properties;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.MessageSourceAccessor;

public class ApplicationMessageSource extends ApplicationObjectSupport {
	
	public String getMessage(String key) {  
		
        MessageSourceAccessor messageSourceAccessor = this.getMessageSourceAccessor();   
        return messageSourceAccessor.getMessage(key);  
    }  
	
	public Properties getMessages (String keys[]) {
		
		MessageSourceAccessor messageSourceAccessor = this.getMessageSourceAccessor(); 
		Properties p = new Properties();
		if (keys != null) {
			for (String key : keys) {
				p.put(key, messageSourceAccessor.getMessage(key));
			}
		}
		return p;
	}

}
