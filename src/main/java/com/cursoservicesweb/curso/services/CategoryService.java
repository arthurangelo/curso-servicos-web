package com.cursoservicesweb.curso.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.cursoservicesweb.curso.entities.Product;
import com.cursoservicesweb.curso.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.cursoservicesweb.curso.dto.CategoryDTO;
import com.cursoservicesweb.curso.entities.Category;
import com.cursoservicesweb.curso.repositories.CategoryRepository;
import com.cursoservicesweb.curso.services.exceptions.DatabaseException;
import com.cursoservicesweb.curso.services.exceptions.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	@Autowired
	private ProductRepository productRepository;
	
	public List<CategoryDTO> findAll(){
		List<Category> list = repository.findAll();
		return list.stream().map(e -> new CategoryDTO(e)).collect(Collectors.toList());
		
	}
	
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity =  obj.orElseThrow( () -> new ResourceNotFoundException(id));
		
		return new CategoryDTO(entity);
	}
	
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = dto.toEntity();
		entity = repository.save(entity);
		return new CategoryDTO(entity);
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
	
	@Transactional
	public CategoryDTO update(Long id , CategoryDTO obj) {
		try {
			Category entity = repository.getOne(id);
			entity.setNome(obj.getName());
			entity =  repository.save(entity);
			return new CategoryDTO(entity);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	@Transactional(readOnly = true)
	public List<CategoryDTO> findByProduct( Long productId) {
		Product product = productRepository.getOne(productId);

		Set<Category> set = product.getCategories();

		return set.stream().map(e -> new CategoryDTO(e)).collect(Collectors.toList());
	}
}
