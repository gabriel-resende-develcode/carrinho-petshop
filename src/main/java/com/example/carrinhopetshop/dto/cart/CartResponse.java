package com.example.carrinhopetshop.dto.cart;

import com.example.carrinhopetshop.model.Client;
import com.example.carrinhopetshop.model.Cart;
import com.example.carrinhopetshop.model.CartItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(Long id,
                           BigDecimal totalValue,
                           Client client,
                           @JsonIgnoreProperties("cart") List<CartItem> items) {

    public CartResponse(Cart entity){
        this(entity.getId(), entity.getTotalValue(), entity.getClient(), entity.getItems());
    }
}
