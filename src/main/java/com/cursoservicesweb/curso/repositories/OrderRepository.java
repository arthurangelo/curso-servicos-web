package com.cursoservicesweb.curso.repositories;

import com.cursoservicesweb.curso.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cursoservicesweb.curso.entities.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

        List<Order> findByClient(User client);
}
