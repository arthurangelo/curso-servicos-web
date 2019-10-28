package com.cursoservicesweb.curso.resources;

import com.cursoservicesweb.curso.dto.PaymentDTO;
import com.cursoservicesweb.curso.services.PaymentService;
import com.cursoservicesweb.curso.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/payments")
public class PaymentResource {
	
	@Autowired
	private PaymentService service;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<PaymentDTO>> findAll(){
		List<PaymentDTO> list = service.findAll();
		
		return ResponseEntity.ok().body(list);
		
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<PaymentDTO> findbyId(@PathVariable Long id){
		PaymentDTO obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	

}
