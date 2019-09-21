package com.cursoservicesweb.curso.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.cursoservicesweb.curso.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryrepository;
	
	public List<ProductDTO> findAll(){
		List<Product> list = repository.findAll();
		return list.stream().map(e -> new ProductDTO(e)).collect(Collectors.toList());
		
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

	private void setProductCategories(Product entity, List<CategoryDTO> categories) {
		entity.getCategories().clear();
		for(CategoryDTO categoryDTO : categories) {
			Category category = categoryrepository.getOne(categoryDTO.getId());
			entity.getCategories().add(category);
		}
	}
}
