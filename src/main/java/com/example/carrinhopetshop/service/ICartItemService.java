package com.example.carrinhopetshop.service;

import com.example.carrinhopetshop.dto.cart.CartRequest;
import com.example.carrinhopetshop.dto.cartItem.CartItemResponse;
import com.example.carrinhopetshop.model.CartItem;

import java.util.List;

public interface ICartItemService {

    CartItemResponse removeItemFromCart(Long itemId, CartRequest request);

    void deleteCartItems(Long cartId);

    public void deleteById(Long id);

    CartItem save(CartItem item);

    List<CartItemResponse> getAll();

    CartItemResponse getById(Long id);

    boolean productIsAlreadyInTheCart(Long productId, Long cartId);

    void updateCartItemQuantity(int quantity, Long productId, Long cartId);
}
