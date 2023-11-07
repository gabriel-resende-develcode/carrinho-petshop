package com.example.carrinhopetshop.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sales")
@EqualsAndHashCode(of = "id")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal totalValue;

    private LocalDateTime purchaseDate;

    @OneToMany(mappedBy = "sale")
    private List<ShoppingCartItem> items;

    @ManyToOne
    private Client client;
}
