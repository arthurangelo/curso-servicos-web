package com.cursoservicesweb.curso.resources.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5571789713501552329L;
	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError(Instant timesInstant, Integer status, String error, String message, String pah) {
		super(timesInstant,status,error,message,pah);
		
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}
	
	public void addError(String fieldName,String message) {
		errors.add(new FieldMessage(fieldName,message));
	}

}
