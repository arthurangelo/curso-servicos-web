package com.cursoservicesweb.curso.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cursoservicesweb.curso.entities.Product;

public class ProductCategoriesDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5642380515338921104L;
	private String name;
	private String description;
	private Double price;
	private String imgUrl;

	private List<CategoryDTO> categories = new ArrayList<>();

	public ProductCategoriesDTO() {

	}

	public ProductCategoriesDTO(String name, String description, Double price, String imgUrl) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}

	public ProductCategoriesDTO(Product entity) {
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.imgUrl = entity.getImgUrl();

	}
	
	public Product toEntity() {
		return new Product( null, name,  description, price, imgUrl);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
