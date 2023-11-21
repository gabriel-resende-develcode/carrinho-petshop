package com.example.carrinhopetshop.service;

import com.example.carrinhopetshop.dto.category.CategoryRequest;
import com.example.carrinhopetshop.dto.category.CategoryResponse;

import java.util.List;

public interface ICategoryService {

    CategoryResponse create(CategoryRequest request);

    List<CategoryResponse> getAll();

    CategoryResponse getById(Long id);

    void delete(Long id);
}
