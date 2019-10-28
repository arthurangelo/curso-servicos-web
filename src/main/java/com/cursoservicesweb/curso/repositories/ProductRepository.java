package com.cursoservicesweb.curso.repositories;

import com.cursoservicesweb.curso.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cursoservicesweb.curso.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Product obj INNER JOIN obj.categories cats WHERE :category IN cats")
    Page<Product> findByCateogry(Category category, Pageable pageable);
}
