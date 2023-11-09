package com.example.carrinhopetshop.service.Implementation;

import com.example.carrinhopetshop.dto.cartItem.CartItemResponse;
import com.example.carrinhopetshop.model.CartItem;
import com.example.carrinhopetshop.repository.CartItemRepository;
import com.example.carrinhopetshop.service.ICartItemService;
import com.example.carrinhopetshop.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public List<CartItemResponse> getAll() {
        return cartItemRepository.findAll().stream().map(CartItemResponse::new).toList();
    }

    @Override
    public CartItemResponse getById(Long id) {
        return new CartItemResponse(cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no cart item with this id. Id " + id)));
    }

    @Override
    @Transactional
    public CartItem save(CartItem item) {
        return cartItemRepository.save(item);
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long cartId) {
        cartItemRepository.deleteCartItemsByCart_Id(cartId);
    }

    @Override
    public boolean productIsAlreadyInTheCart(Long productId, Long cartId) {
        var cartItem = cartItemRepository.findProductInTheCart(productId, cartId);
        return cartItem != null;
    }

    @Override
    @Transactional
    public void updateCartItemQuantity(int quantity, Long productId, Long cartId) {
        var cartItem = cartItemRepository.findProductInTheCart(cartId, productId);
        cartItem.incrementQuantity(quantity);
    }
}
