package com.example.carrinhopetshop.dto.cart;

import com.example.carrinhopetshop.model.Client;
import com.example.carrinhopetshop.model.Product;
import jakarta.validation.constraints.NotNull;

public record CartRequest(@NotNull int quantity,
                          @NotNull Product product,
                          @NotNull Client client
) {
}
