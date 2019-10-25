package com.cursoservicesweb.curso.repositories;

import com.cursoservicesweb.curso.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
