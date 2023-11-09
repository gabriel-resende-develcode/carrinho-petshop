package com.example.carrinhopetshop.service;

import com.example.carrinhopetshop.dto.cart.CartRequest;
import com.example.carrinhopetshop.dto.cart.CartResponse;
import com.example.carrinhopetshop.dto.order.OrderResponse;

import java.util.List;

public interface ICartService {

    CartResponse addItemToCart(CartRequest request, Long id);

    List<CartResponse> getAllCarts();

    CartResponse getCartById(Long id);

    void clearCart(Long id);

    OrderResponse finalizePurchase(Long id);
}
