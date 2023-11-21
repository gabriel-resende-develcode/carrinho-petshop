package com.example.carrinhopetshop.dto.category;

import com.example.carrinhopetshop.model.Category;

public record CategoryResponse(Long id, String name) {

    public CategoryResponse(Category entity){
        this(entity.getId(), entity.getName());
    }
}
