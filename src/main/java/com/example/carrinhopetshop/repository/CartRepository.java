package com.example.carrinhopetshop.repository;

import com.example.carrinhopetshop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
