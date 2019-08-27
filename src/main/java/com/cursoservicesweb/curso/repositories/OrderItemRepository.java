package com.cursoservicesweb.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursoservicesweb.curso.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
