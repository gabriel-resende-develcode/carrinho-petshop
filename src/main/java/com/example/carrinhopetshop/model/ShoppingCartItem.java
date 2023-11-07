package com.example.carrinhopetshop.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Entity
@Table(name = "shopping_cart_items")
@EqualsAndHashCode(of = "id")
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShoppingCart shoppingCart;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sale sale;
}
