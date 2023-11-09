package com.example.carrinhopetshop.controller;

import com.example.carrinhopetshop.dto.cart.CartItemRequest;
import com.example.carrinhopetshop.dto.cart.CartResponse;
import com.example.carrinhopetshop.dto.order.OrderResponse;
import com.example.carrinhopetshop.service.ICartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final ICartService cartService;

    @Autowired
    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCartById(id));
    }

    @PostMapping(value = "/{cartId}")
    public ResponseEntity<CartResponse> addItem(@RequestBody @Valid CartItemRequest request, @PathVariable Long cartId, UriComponentsBuilder uriBuilder) {
        var cartResponse = cartService.addItemToCart(request, cartId);
        var uri = uriBuilder.path("/api/cart/{id}").buildAndExpand(cartResponse.id()).toUri();
        return ResponseEntity.created(uri).body(cartResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> clearCart(@PathVariable Long id) {
        cartService.clearCart(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/buy/{id}")
    public ResponseEntity<OrderResponse> finalizePurchase(@PathVariable Long id){
        return ResponseEntity.ok(cartService.finalizePurchase(id));
    }
}
