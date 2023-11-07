package com.example.carrinhopetshop.model;

import com.example.carrinhopetshop.dto.category.CategoryRequest;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@EqualsAndHashCode(of = "id")
@Getter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    public Category(CategoryRequest request) {
        name = request.name();
    }
}
