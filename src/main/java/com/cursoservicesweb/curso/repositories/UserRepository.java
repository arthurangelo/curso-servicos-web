package com.cursoservicesweb.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursoservicesweb.curso.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
