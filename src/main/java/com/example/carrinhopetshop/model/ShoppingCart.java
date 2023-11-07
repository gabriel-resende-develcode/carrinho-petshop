package com.example.carrinhopetshop.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "shooping_carts")
@EqualsAndHashCode(of = "id")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal totalValue;

    @OneToOne
    private Client client;

    @OneToMany(mappedBy = "shoppingCart")
    private List<ShoppingCartItem> items;
}
