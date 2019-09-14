package com.cursoservicesweb.curso.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StandardError implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1377699739040913198L;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",timezone = "GMT")
	private Instant timesInstant;
	private Integer status;
	private String error;
	private String message;
	private String pah;
	
	public StandardError() {
		
	}
	
	

	public StandardError(Instant timesInstant, Integer status, String error, String message, String pah) {
		super();
	
		this.timesInstant = timesInstant;
		this.status = status;
		this.error = error;
		this.message = message;
		this.pah = pah;
	}



	public Instant getTimesInstant() {
		return timesInstant;
	}

	public void setTimesInstant(Instant timesInstant) {
		this.timesInstant = timesInstant;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPah() {
		return pah;
	}

	public void setPah(String pah) {
		this.pah = pah;
	}
	
	

}
