package com.example.carrinhopetshop.model;

import com.example.carrinhopetshop.dto.cart.CartItemRequest;
import com.example.carrinhopetshop.dto.cart.CartResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal totalValue;

    @OneToOne
    private Client client;

    @JsonIgnoreProperties("cart")
    @OneToMany(mappedBy = "cart")
    private List<CartItem> items = new ArrayList<>(50);

    public Cart(BigDecimal totalValue, Client client) {
        this.client = client;
        this.totalValue = totalValue;
    }

    public Cart(CartResponse response) {
        id = response.id();
        totalValue = response.totalValue();
        client = response.client();
        items = response.items();
    }

    public void addItemToTheCart(CartItem item){
        items.add(item);
    }

    public void incrementTotalValue(BigDecimal value){
        totalValue = totalValue.add(value);
    }
}
