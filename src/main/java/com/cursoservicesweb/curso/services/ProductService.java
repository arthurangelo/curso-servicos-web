package com.cursoservicesweb.curso.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursoservicesweb.curso.dto.CategoryDTO;
import com.cursoservicesweb.curso.dto.ProductCategoriesDTO;
import com.cursoservicesweb.curso.dto.ProductDTO;
import com.cursoservicesweb.curso.dto.UserDTO;
import com.cursoservicesweb.curso.dto.UserInsertDTO;
import com.cursoservicesweb.curso.entities.Category;
import com.cursoservicesweb.curso.entities.Product;
import com.cursoservicesweb.curso.entities.User;
import com.cursoservicesweb.curso.repositories.CategoryRepository;
import com.cursoservicesweb.curso.repositories.ProductRepository;
import com.cursoservicesweb.curso.services.exceptions.DatabaseException;
import com.cursoservicesweb.curso.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryrepository;
	
	public Page<ProductDTO> findAllPaged(Pageable pageable){
		Page<Product> list = repository.findAll(pageable);
		return list.map(e -> new ProductDTO(e));
		
	}
	
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity =  obj.orElseThrow( () -> new ResourceNotFoundException(id));
		
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO insert(ProductCategoriesDTO dto) {
		Product entity = dto.toEntity();
		setProductCategories(entity,dto.getCategories());
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id , ProductCategoriesDTO obj) {
		try {
			Product entity = repository.getOne(id);
			updateData(entity,obj);
			entity =  repository.save(entity);
			return new ProductDTO(entity);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		
		
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
	
	private void updateData(Product entity, ProductCategoriesDTO dto) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		
		if(dto.getCategories() != null && dto.getCategories().size() > 0 ) {
			setProductCategories(entity, dto.getCategories());
		}

		
	}


	private void setProductCategories(Product entity, List<CategoryDTO> categories) {
		entity.getCategories().clear();
		for(CategoryDTO categoryDTO : categories) {
			Category category = categoryrepository.getOne(categoryDTO.getId());
			entity.getCategories().add(category);
		}
	}

	@Transactional(readOnly = true)
    public Page<ProductDTO> findByCategoryPaged(Long categoryId, Pageable pageable) {
		Category category = categoryrepository.getOne(categoryId);
		Page<Product> products = repository.findByCateogry(category,pageable);
		return products.map(e -> new ProductDTO(e));
     }
}
