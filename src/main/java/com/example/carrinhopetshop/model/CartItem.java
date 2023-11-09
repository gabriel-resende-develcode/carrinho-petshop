package com.example.carrinhopetshop.model;

import com.example.carrinhopetshop.dto.cart.CartItemRequest;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
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

    public CartItem(CartItemRequest request, Cart cart) {
        unitPrice = request.product().getPrice();
        quantity = request.quantity();
        product = request.product();
        this.cart = cart;
    }

    public void incrementQuantity(int quantity) {
        this.quantity += quantity;
    }
}
