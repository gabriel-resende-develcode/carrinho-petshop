package com.example.carrinhopetshop.service;

import com.example.carrinhopetshop.dto.order.OrderResponse;
import com.example.carrinhopetshop.model.Order;

import java.util.List;

public interface IOrderService {

    OrderResponse create(Order order);

    List<OrderResponse> getAll();
}
