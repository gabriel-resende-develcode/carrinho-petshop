package com.example.carrinhopetshop.dto.cartItem;

import com.example.carrinhopetshop.model.CartItem;
import com.example.carrinhopetshop.model.Order;
import com.example.carrinhopetshop.model.Product;
import com.example.carrinhopetshop.model.Cart;

import java.math.BigDecimal;

public record CartItemResponse(Long id, BigDecimal unitPrice, int quantity,
                               Product product) {

    public CartItemResponse(CartItem entity) {
        this(entity.getId(), entity.getUnitPrice(), entity.getQuantity(),
                entity.getProduct());
    }
}
