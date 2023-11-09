package com.example.carrinhopetshop.service;

import com.example.carrinhopetshop.dto.cart.CartItemRequest;
import com.example.carrinhopetshop.dto.cart.CartResponse;

import java.util.List;

public interface ICartService {

    CartResponse addItemToCart(CartItemRequest request, Long id);

    List<CartResponse> getAllCarts();

    CartResponse getCartById(Long id);

    void clearCart(Long id);

    void finalizePurchase(Long id);
}
