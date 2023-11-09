package com.example.carrinhopetshop.dto.cart;

import com.example.carrinhopetshop.model.Client;
import com.example.carrinhopetshop.model.Product;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CartItemRequest(@NotNull int quantity,
                              @NotNull Product product,
                              @NotNull Client client
) {
}
