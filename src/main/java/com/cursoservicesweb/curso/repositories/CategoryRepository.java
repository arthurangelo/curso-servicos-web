package com.cursoservicesweb.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursoservicesweb.curso.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
