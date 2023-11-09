package com.example.carrinhopetshop.repository;

import com.example.carrinhopetshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
