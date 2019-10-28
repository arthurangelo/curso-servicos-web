package com.cursoservicesweb.curso.resources;

import com.cursoservicesweb.curso.dto.CredentialsDTO;
import com.cursoservicesweb.curso.dto.EmailDTO;
import com.cursoservicesweb.curso.dto.TokenDTO;
import com.cursoservicesweb.curso.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> authenticate(@RequestBody CredentialsDTO dto){
        TokenDTO tokenDTO = service.authenticate(dto);
        return ResponseEntity.ok().body(tokenDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(){
        TokenDTO tokenDTO = service.refresToken();
        return ResponseEntity.ok().body(tokenDTO);
    }

    @PostMapping("/forgot")
    public ResponseEntity<Void> forgot(@RequestBody EmailDTO dto){
       service.sendNewPassword(dto.getEmail());
        return ResponseEntity.noContent().build();
    }
}
