package com.cursoservicesweb.curso.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2051661744862106304L;
	
	public ResourceNotFoundException(Object id) {
		super("Resource not found. ID " + id);
	}

	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
