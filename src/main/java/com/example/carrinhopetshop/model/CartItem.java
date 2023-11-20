package com.example.carrinhopetshop.model;

import com.example.carrinhopetshop.dto.cart.CartRequest;
import com.example.carrinhopetshop.dto.cartItem.CartItemResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    public CartItem(CartRequest request, Cart cart) {
        unitPrice = request.product().getPrice();
        quantity = request.quantity();
        product = request.product();
        this.cart = cart;
    }

    public CartItem(CartItemResponse response) {
        id = response.id();
        unitPrice = response.unitPrice();
        quantity = response.quantity();
        product = response.product();
    }

    public void incrementQuantity(int quantity) {
        this.quantity += quantity;
    }

    public int decreaseItemQuantity(int newQuantity) {
        this.quantity -= newQuantity;
        return this.quantity;
    }
}
