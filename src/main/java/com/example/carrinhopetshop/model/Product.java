package com.example.carrinhopetshop.model;

import com.example.carrinhopetshop.dto.product.ProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@EqualsAndHashCode(of = "id")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 300)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    private Category category;

    public Product(ProductRequest request){
        name = request.name();
        description = request.description();
        price = request.price();
        category = request.category();
    }
}
