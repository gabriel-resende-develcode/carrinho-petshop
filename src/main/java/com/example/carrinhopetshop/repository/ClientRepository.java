package com.example.carrinhopetshop.repository;

import com.example.carrinhopetshop.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
