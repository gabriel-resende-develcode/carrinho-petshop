package com.example.carrinhopetshop.service.Implementation;

import com.example.carrinhopetshop.dto.cart.CartRequest;
import com.example.carrinhopetshop.dto.cart.CartResponse;
import com.example.carrinhopetshop.dto.order.OrderResponse;
import com.example.carrinhopetshop.model.Cart;
import com.example.carrinhopetshop.model.CartItem;
import com.example.carrinhopetshop.model.Order;
import com.example.carrinhopetshop.repository.CartRepository;
import com.example.carrinhopetshop.service.ICartItemService;
import com.example.carrinhopetshop.service.ICartService;
import com.example.carrinhopetshop.service.IOrderService;
import com.example.carrinhopetshop.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;

    private final ICartItemService cartItemService;

    private final IOrderService orderService;

    @Autowired
    public CartService(CartRepository cartRepository, ICartItemService cartItemService, IOrderService orderService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.orderService = orderService;
    }

    @Transactional
    @Override
    public CartResponse addItemToCart(CartRequest request, Long cartId) {
        CartResponse cartResponse = null;

        if (!cartRepository.existsById(cartId)) {
            cartResponse = addItemToNewCart(request, cartId);
        } else if (cartItemService.productIsAlreadyInTheCart(request.product().getId(), cartId)) {
            cartResponse = addExistingItemToCart(request, cartId);
        } else {
            cartResponse = addNewItemToCart(request, cartId);
        }
        return cartResponse;
    }

    private CartResponse addNewItemToCart(CartRequest request, Long cartId) {
        var cart = new Cart(getCartById(cartId));
        cart.incrementTotalValue(calculateTotalValue(request.product().getPrice(), request.quantity()));
        cart.addItemToTheCart(cartItemService.save(new CartItem(request, cart)));
        return new CartResponse(cart);
    }

    private CartResponse addExistingItemToCart(CartRequest request, Long cartId) {
        var cart = new Cart(getCartById(cartId));
        cart.incrementTotalValue(calculateTotalValue(request.product().getPrice(), request.quantity()));
        cartItemService.updateCartItemQuantity(request.quantity(), request.product().getId(), cartId);
        cartRepository.save(cart);
        return new CartResponse(cart);
    }

    private CartResponse addItemToNewCart(CartRequest request, Long cartId) {
        var totalValue = calculateTotalValue(request.product().getPrice(), request.quantity());
        var cart = new Cart(totalValue, request.client());
        cart = cartRepository.save(cart);
        var cartItem = new CartItem(request, cart);
        cart.addItemToTheCart(cartItemService.save(cartItem));
        return new CartResponse(cart);
    }

    private BigDecimal calculateTotalValue(BigDecimal unitPrice, int quantity) {
        return unitPrice.multiply(new BigDecimal(quantity));
    }

    @Override
    public List<CartResponse> getAllCarts() {
        return cartRepository.findAll().stream().map(CartResponse::new).toList();
    }

    @Override
    public CartResponse getCartById(Long id) {
        return new CartResponse(cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no shoopingCart with this id. Id " + id)));
    }

    @Override
    @Transactional
    public void clearCart(Long cartId) {
        getCartById(cartId);
        cartItemService.removeItemFromCart(cartId);
        cartRepository.deleteById(cartId);
    }

    @Transactional
    @Override
    public OrderResponse finalizePurchase(Long cartId) {
        var cart = new Cart(getCartById(cartId));
        var order = new Order(cart);
        clearCart(cartId);
        return orderService.create(order);
    }
}
