package com.example.carrinhopetshop.controller;

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

    @PostMapping
    public ResponseEntity<CartItem> save(@RequestBody CartItem requestItem, UriComponentsBuilder uriBuilder) {
        var item = cartItemService.save(requestItem);
        var uri = uriBuilder.path(("/api/cart/items/{id}")).buildAndExpand(item.getId()).toUri();
        return ResponseEntity.created(uri).body(item);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable Long id) {
        cartItemService.removeItemFromCart(id);
        return ResponseEntity.noContent().build();
    }
}
