package com.example.carrinhopetshop.service.Implementation;

import com.example.carrinhopetshop.dto.cart.CartRequest;
import com.example.carrinhopetshop.dto.cart.CartResponse;
import com.example.carrinhopetshop.model.Cart;
import com.example.carrinhopetshop.model.CartItem;
import com.example.carrinhopetshop.model.Category;
import com.example.carrinhopetshop.model.Client;
import com.example.carrinhopetshop.model.Order;
import com.example.carrinhopetshop.model.Product;
import com.example.carrinhopetshop.repository.CartRepository;
import com.example.carrinhopetshop.service.ICartItemService;
import com.example.carrinhopetshop.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @InjectMocks
    @Spy
    private CartService cartService;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ICartItemService cartItemService;
    @Mock
    private OrderService orderService;
    @Captor
    private ArgumentCaptor<Cart> cartCaptor;
    @Captor
    private ArgumentCaptor<CartItem> itemCaptor;
    @Captor
    private ArgumentCaptor<Order> orderCaptor;
    private static Category category;
    private static Product product;
    private static Client client;
    private static CartRequest cartRequest;
    private static Cart cart;
    private static CartItem item;
    private static BigDecimal totalValue;

    @BeforeAll
    static void init() {
        category = new Category(1L, "category");
        product = new Product(1L, "name", "description", new BigDecimal("19.90"), category);
        client = new Client(1L, "name");
        cartRequest = new CartRequest(2, product, client);
        cart = new Cart(new BigDecimal("19.90"), client);
        item = new CartItem(cartRequest, cart);
        totalValue = product.getPrice().multiply(new BigDecimal(cartRequest.quantity()));
    }

    @Test
    void shouldReturnACartResponseWhenCreateACartWithAnItem() {
        Cart savedCart = new Cart(1L, totalValue, client, new ArrayList<>());
        when(cartRepository.save(cartCaptor.capture())).thenReturn(savedCart);

        var response = cartService.createCart(cartRequest);

        then(cartRepository).should().save(cartCaptor.capture());
        then(cartItemService).should().save(itemCaptor.capture());
        then(cartRepository).shouldHaveNoMoreInteractions();
        assertEquals(1L, response.id());
        assertEquals(totalValue, response.totalValue());
        assertEquals(client, response.client());
        assertEquals(1, response.items().size());
    }

    @Test
    void shouldIncrementQuantityAndTotalValueWhenAddARepeatItemToACar() {
        Cart savedCart = new Cart(1L, totalValue, client, new ArrayList<>());
        Cart newCart = new Cart(1L, BigDecimal.ZERO, client, Arrays.asList(item));

        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(newCart));
        when(cartRepository.save(cartCaptor.capture())).thenReturn(savedCart);
        given(cartItemService.productIsAlreadyInTheCart(anyLong(), anyLong())).willReturn(true);

        var response = cartService.addItemToCart(cartRequest, 1L);

        then(cartRepository).should().save(cartCaptor.capture());
        then(cartItemService).should().updateCartItemQuantity(cartRequest.quantity(), cartRequest.product().getId(), 1L);
        assertEquals(1L, response.id());
        assertEquals(totalValue, response.totalValue());
        assertEquals(client, response.client());
        assertEquals(1, response.items().size());
    }

    @Test
    void shouldIncrementTotalValueAndAddANewItemToCartWhenAddANewItemToCart() {
        Cart savedCart = new Cart(1L, totalValue, client, new ArrayList<>());
        Cart newCart = new Cart(1L, BigDecimal.ZERO, client, new ArrayList<>());

        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(newCart));
        when(cartRepository.save(cartCaptor.capture())).thenReturn(savedCart);
        given(cartItemService.productIsAlreadyInTheCart(anyLong(), anyLong())).willReturn(false);

        var response = cartService.addItemToCart(cartRequest, 1L);

        then(cartRepository).should().save(cartCaptor.capture());
        then(cartItemService).should().save(itemCaptor.capture());
        then(cartRepository).shouldHaveNoMoreInteractions();
        assertEquals(1L, response.id());
        assertEquals(totalValue, response.totalValue());
        assertEquals(client, response.client());
        assertEquals(1, response.items().size());
    }

    @Test
    void shouldGetAllCarts() {
        given(cartRepository.findAll()).willReturn(List.of(cart));

        var response = cartService.getAllCarts();

        assertEquals(List.of(new CartResponse(cart)), response);
        assertFalse(response.isEmpty());
    }

    @Test
    void shouldReturnCartResponseByGetCartById() {
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));

        var response = cartService.getCartById(1L);

        assertEquals(new CartResponse(cart), response);
        then(cartRepository).should().findById(anyLong());
        then(cartRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrownResourceNotFoundExceptionOnTryToGetANonExistingCartById() {
        assertThrows(ResourceNotFoundException.class, () -> cartService.clearCart(anyLong()));
    }

    @Test
    void clearCartShouldDeleteCartAndDeleteCartItems() {
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));

        cartService.clearCart(1L);

        verify(cartItemService, times(1)).deleteCartItems(anyLong());
        verify(cartRepository, times(1)).deleteById(anyLong());
        then(cartRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrownResourceNotFoundExceptionOnTryToClearANonExistingCart() {
        assertThrows(ResourceNotFoundException.class, () -> cartService.clearCart(anyLong()));
    }

    @Test
    void finalizePurchaseShouldClearCartAndCreateANewOrder() {
        when(cartRepository.findById(anyLong())).thenReturn(Optional.of(cart));

        cartService.finalizePurchase(1L);

        verify(cartService, times(1)).clearCart(1L);
        verify(orderService, times(1)).create(orderCaptor.capture());
        var order = orderCaptor.getValue();
        assertNotNull(order);
        assertEquals(new Order(cart), order);
    }

    @Test
    void shouldThrownResourceNotFoundExceptionOnTryFinalizePurchaseOnANonExistingCart() {
        assertThrows(ResourceNotFoundException.class, () -> cartService.finalizePurchase(anyLong()));
    }
}