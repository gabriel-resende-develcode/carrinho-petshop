package com.example.carrinhopetshop.dto.product;

import com.example.carrinhopetshop.model.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(@NotBlank String name,
                             String description,
                             @NotNull BigDecimal price,
                             @NotNull Category category) {
}
