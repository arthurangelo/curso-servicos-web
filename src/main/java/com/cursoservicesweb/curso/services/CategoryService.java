package com.cursoservicesweb.curso.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.cursoservicesweb.curso.entities.Category;
import com.cursoservicesweb.curso.entities.User;
import com.cursoservicesweb.curso.repositories.CategoryRepository;
import com.cursoservicesweb.curso.services.exceptions.DatabaseException;
import com.cursoservicesweb.curso.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	public List<Category> findAll(){
		return repository.findAll();
	}
	
	public Category findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		return obj.orElseThrow( () -> new ResourceNotFoundException(id));
	}
	
	public Category insert(Category obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public Category update(Long id , Category obj) {
		try {
			return repository.save(obj);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
				
	}

}
