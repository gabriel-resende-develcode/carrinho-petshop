package com.example.carrinhopetshop.controller;

import com.example.carrinhopetshop.dto.cart.CartRequest;
import com.example.carrinhopetshop.dto.cartItem.CartItemResponse;
import com.example.carrinhopetshop.model.CartItem;
import com.example.carrinhopetshop.service.ICartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/cart/items")
public class CartItemController {

    private final ICartItemService cartItemService;

    @Autowired
    public CartItemController(ICartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getAll() {
        return ResponseEntity.ok(cartItemService.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CartItemResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cartItemService.getById(id));
    }

    @PutMapping(value = "/{itemId}")
    public ResponseEntity<?> removeItem(@PathVariable Long itemId, @RequestBody CartRequest request) {
        var response = cartItemService.removeItemFromCart(itemId, request);
        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }
}
