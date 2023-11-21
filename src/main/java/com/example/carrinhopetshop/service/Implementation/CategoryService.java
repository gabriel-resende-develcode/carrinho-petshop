package com.example.carrinhopetshop.service.Implementation;

import com.example.carrinhopetshop.dto.category.CategoryRequest;
import com.example.carrinhopetshop.dto.category.CategoryResponse;
import com.example.carrinhopetshop.model.Category;
import com.example.carrinhopetshop.repository.CategoryRepository;
import com.example.carrinhopetshop.service.ICategoryService;
import com.example.carrinhopetshop.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryResponse create(CategoryRequest request) {
        return new CategoryResponse(categoryRepository.save(new Category(request)));
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream().map(CategoryResponse::new).toList();
    }

    @Override
    public CategoryResponse getById(Long id) {
        return new CategoryResponse(categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no category with this id. Id " + id)));
    }

    @Override
    public void delete(Long id) {
        getById(id);
        categoryRepository.deleteById(id);
    }
}
