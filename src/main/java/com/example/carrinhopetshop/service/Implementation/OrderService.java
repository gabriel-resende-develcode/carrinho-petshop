package com.example.carrinhopetshop.service.Implementation;

import com.example.carrinhopetshop.dto.order.OrderResponse;
import com.example.carrinhopetshop.model.Order;
import com.example.carrinhopetshop.repository.OrderRepository;
import com.example.carrinhopetshop.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public OrderResponse create(Order order) {
        return new OrderResponse(orderRepository.save(order));
    }

    @Override
    public List<OrderResponse> getAll() {
        return orderRepository.findAll().stream().map(OrderResponse::new).toList();
    }
}
