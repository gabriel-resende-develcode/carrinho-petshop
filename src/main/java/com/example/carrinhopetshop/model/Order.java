package com.example.carrinhopetshop.model;

import com.example.carrinhopetshop.model.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal totalValue;

    private LocalDateTime purchaseDate;

    @OneToMany(mappedBy = "order")
    private List<CartItem> items;

    @ManyToOne
    private Client client;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(Cart cart){
        totalValue = cart.getTotalValue();
        items = cart.getItems();
        client = cart.getClient();
        purchaseDate = LocalDateTime.now();
        status = OrderStatus.WAITING_PAYMENT;
    }
}
