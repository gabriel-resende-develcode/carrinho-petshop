package com.example.carrinhopetshop.service;

import com.example.carrinhopetshop.dto.cartItem.CartItemResponse;
import com.example.carrinhopetshop.model.CartItem;

import java.util.List;

public interface ICartItemService {

    void removeItemFromCart(Long id);

    CartItem save(CartItem item);

    List<CartItemResponse> getAll();

    CartItemResponse getById(Long id);

    boolean productIsAlreadyInTheCart(Long productId, Long cartId);

    void updateCartItemQuantity(int quantity, Long productId, Long cartId);
}
