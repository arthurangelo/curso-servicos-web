package com.cursoservicesweb.curso.services;

import com.cursoservicesweb.curso.dto.PaymentDTO;
import com.cursoservicesweb.curso.dto.PaymentDTO;
import com.cursoservicesweb.curso.entities.Order;
import com.cursoservicesweb.curso.entities.Payment;
import com.cursoservicesweb.curso.entities.Payment;
import com.cursoservicesweb.curso.repositories.OrderRepository;
import com.cursoservicesweb.curso.repositories.PaymentRepository;
import com.cursoservicesweb.curso.services.exceptions.DatabaseException;
import com.cursoservicesweb.curso.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private OrderRepository orderRepository;

    public List<PaymentDTO> findAll(){
        List<Payment> list = repository.findAll();
        return list.stream().map(e -> new PaymentDTO(e)).collect(Collectors.toList());

    }

    public PaymentDTO findById(Long id) {
        Optional<Payment> obj = repository.findById(id);
        Payment entity =  obj.orElseThrow( () -> new ResourceNotFoundException(id));

        return new PaymentDTO(entity);
    }

    @Transactional
    public PaymentDTO insert(PaymentDTO dto) {
       Order order = orderRepository.getOne(dto.getOrderId());
       Payment payment = new Payment(null,dto.getMoment(),order);
       order.setPayment(payment);
       orderRepository.save(order);

       return new PaymentDTO(order.getPayment());

    }
    

    @Transactional
    public PaymentDTO update(Long id , PaymentDTO obj) {
        try {
            Payment entity = repository.getOne(id);
            entity.setMoment(obj.getMoment());
            entity =  repository.save(entity);
            return new PaymentDTO(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }
}
