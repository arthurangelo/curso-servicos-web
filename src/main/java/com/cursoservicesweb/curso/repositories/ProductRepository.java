package com.cursoservicesweb.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursoservicesweb.curso.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
