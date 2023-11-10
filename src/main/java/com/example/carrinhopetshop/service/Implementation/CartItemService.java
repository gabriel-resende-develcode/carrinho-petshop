package com.example.carrinhopetshop.service.Implementation;

import com.example.carrinhopetshop.dto.cart.CartRequest;
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
    public CartItemResponse removeItemFromCart(Long itemId, CartRequest request) {
        var cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("No item with this id. Id" + itemId));
        var requestQuantity = request.quantity();
        var currentQuantity = cartItem.getQuantity();

        if (requestQuantity < 0) {
            throw new IllegalArgumentException("You can't remove a negative quantity of items");
        } else if (requestQuantity >= currentQuantity) {
            deleteItemById(itemId);
            return null;
        } else {
            var newQuantity = cartItem.decreaseItemQuantity(requestQuantity);
            cartItem.getCart().decreaseTotalValue(newQuantity, cartItem.getUnitPrice());
            return new CartItemResponse(cartItemRepository.save(cartItem));
        }
    }

    @Override
    @Transactional
    public void deleteItemById(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteCartItems(Long cartId) {
        cartItemRepository.deleteCartItemsByCart_Id(cartId);
    }

    @Override
    public boolean productIsAlreadyInTheCart(Long productId, Long cartId) {
        var cartItem = cartItemRepository.findCartItemByCart_IdAndProduct_Id(cartId, productId);
        return cartItem != null;
    }

    @Override
    @Transactional
    public void updateCartItemQuantity(int quantity, Long productId, Long cartId) {
        var cartItem = cartItemRepository.findCartItemByCart_IdAndProduct_Id(cartId, productId);
        cartItem.incrementQuantity(quantity);
    }
}
