package com.cursoservicesweb.curso.repositories;

import com.cursoservicesweb.curso.entities.Order;
import com.cursoservicesweb.curso.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
