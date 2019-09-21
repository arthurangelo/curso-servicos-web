package com.cursoservicesweb.curso.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursoservicesweb.curso.dto.OrderDTO;
import com.cursoservicesweb.curso.entities.Order;
import com.cursoservicesweb.curso.entities.User;
import com.cursoservicesweb.curso.repositories.OrderRepository;
import com.cursoservicesweb.curso.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	public List<OrderDTO> findAll(){
		List<Order> list = repository.findAll();
		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
		
	}
	
	public OrderDTO findById(Long id) {
		Optional<Order> obj = repository.findById(id);
		Order entity =  obj.orElseThrow( () -> new ResourceNotFoundException(id));
		
		return new OrderDTO(entity);
	}
	


}
