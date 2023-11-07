package com.example.carrinhopetshop.dto.product;

import com.example.carrinhopetshop.model.Category;
import com.example.carrinhopetshop.model.Product;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, String description, BigDecimal price, Category category) {

    public ProductResponse(Product entity){
        this(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice(), entity.getCategory());
    }
}
