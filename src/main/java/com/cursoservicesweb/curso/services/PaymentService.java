package com.cursoservicesweb.curso.services;

import com.cursoservicesweb.curso.dto.CategoryDTO;
import com.cursoservicesweb.curso.dto.PaymentDTO;
import com.cursoservicesweb.curso.entities.Category;
import com.cursoservicesweb.curso.entities.Payment;
import com.cursoservicesweb.curso.repositories.PaymentRepository;
import com.cursoservicesweb.curso.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    public List<PaymentDTO> findAll(){
        List<Payment> list = repository.findAll();
        return list.stream().map(e -> new PaymentDTO(e)).collect(Collectors.toList());

    }

    public PaymentDTO findById(Long id) {
        Optional<Payment> obj = repository.findById(id);
        Payment entity =  obj.orElseThrow( () -> new ResourceNotFoundException(id));

        return new PaymentDTO(entity);
    }
}
