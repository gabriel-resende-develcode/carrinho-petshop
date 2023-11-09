package com.example.carrinhopetshop.repository;

import com.example.carrinhopetshop.model.Cart;
import com.example.carrinhopetshop.model.CartItem;
import com.example.carrinhopetshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    void deleteCartItemsByCart_Id(Long cartId);

    @Query("SELECT DISTINCT i FROM CartItem i WHERE i.cart.id = :cartId AND i.product.id = :productId")
    CartItem findProductInTheCart(@Param("productId") Long productId, @Param("cartId") Long cartId);
}

