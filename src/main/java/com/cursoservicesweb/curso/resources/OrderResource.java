package com.cursoservicesweb.curso.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursoservicesweb.curso.dto.OrderDTO;
import com.cursoservicesweb.curso.entities.Order;
import com.cursoservicesweb.curso.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {
	
	@Autowired
	private OrderService service;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<OrderDTO>> findAll(){
		List<OrderDTO> list = service.findAll();
		
		return ResponseEntity.ok().body(list);
		
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderDTO> findbyId(@PathVariable Long id){
		OrderDTO obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}
