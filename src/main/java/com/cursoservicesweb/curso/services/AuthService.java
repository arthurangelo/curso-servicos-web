package com.cursoservicesweb.curso.services;

import com.cursoservicesweb.curso.Security.JWTUtil;
import com.cursoservicesweb.curso.dto.CredentialsDTO;
import com.cursoservicesweb.curso.dto.TokenDTO;
import com.cursoservicesweb.curso.services.exceptions.JWTAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Transactional(readOnly = true)
    public TokenDTO authenticate(CredentialsDTO credentialsDTO) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    credentialsDTO.getEmail(), credentialsDTO.getPassword());
            authenticationManager.authenticate(authToken);

            String token = jwtUtil.generateToken(credentialsDTO.getEmail());

            return new TokenDTO(credentialsDTO.getEmail(), token);
        } catch (AuthenticationException e) {
            throw new JWTAuthenticationException("Bad Credentials");
        }
    }

}
