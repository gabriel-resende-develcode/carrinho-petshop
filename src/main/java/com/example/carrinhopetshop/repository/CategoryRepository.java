package com.example.carrinhopetshop.repository;

import com.example.carrinhopetshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
