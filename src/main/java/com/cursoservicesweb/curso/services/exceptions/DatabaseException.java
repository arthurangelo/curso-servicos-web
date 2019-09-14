package com.cursoservicesweb.curso.services.exceptions;

public class DatabaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6161289263784289989L;

	public DatabaseException(String message) {
		super(message);
	}
}
