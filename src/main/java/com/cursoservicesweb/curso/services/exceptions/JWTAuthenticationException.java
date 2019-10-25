package com.cursoservicesweb.curso.services.exceptions;

public class JWTAuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public JWTAuthenticationException(String message) {
        super(message);
    }
}
