package com.example.carrinhopetshop.dto.order;

import com.example.carrinhopetshop.model.CartItem;
import com.example.carrinhopetshop.model.Client;
import com.example.carrinhopetshop.model.Order;
import com.example.carrinhopetshop.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(Long id,
                            BigDecimal totalValue,
                            @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime purchaseDate,
                            OrderStatus status,
                            @JsonIgnoreProperties(value = {"cart", "order"})
                            List<CartItem> items,
                            Client client) {

    public OrderResponse(Order entity) {
        this(entity.getId(), entity.getTotalValue(), entity.getPurchaseDate(),
                entity.getStatus(), entity.getItems(), entity.getClient());
    }
}
