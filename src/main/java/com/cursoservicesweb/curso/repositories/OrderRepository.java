package com.cursoservicesweb.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursoservicesweb.curso.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
