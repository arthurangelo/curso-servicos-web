package com.cursoservicesweb.curso.dto;

import com.cursoservicesweb.curso.entities.OrderItem;

import java.io.Serializable;

public class OrderItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer quantity;
    private Double price;
    private Long productId;
    private String productName;
    private String imgUrl;

    public OrderItemDTO(){

    }

    public OrderItemDTO(Integer quantity, Double price, Long productId, String productName, String imgUrl) {
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
        this.productName = productName;
        this.imgUrl = imgUrl;
    }

    public OrderItemDTO(OrderItem orderItem){
        if(orderItem.getProduct() == null){
            throw  new IllegalArgumentException("Product was null");
        }

        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
        this.productId = orderItem.getProduct().getId();
        this.productName = orderItem.getProduct().getName();
        this.imgUrl = orderItem.getProduct().getImgUrl();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
