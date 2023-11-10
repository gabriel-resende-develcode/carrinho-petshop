package com.example.carrinhopetshop.repository;

import com.example.carrinhopetshop.model.Cart;
import com.example.carrinhopetshop.model.CartItem;
import com.example.carrinhopetshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    void deleteCartItemsByCart_Id(Long cartId);

    CartItem findCartItemByCart_IdAndProduct_Id(Long cartId, Long ProductId);
}

