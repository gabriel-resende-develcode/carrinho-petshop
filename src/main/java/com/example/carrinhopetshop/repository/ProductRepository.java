package com.example.carrinhopetshop.repository;

import com.example.carrinhopetshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
