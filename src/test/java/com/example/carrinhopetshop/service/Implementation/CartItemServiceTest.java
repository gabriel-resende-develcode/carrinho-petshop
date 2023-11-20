package com.example.carrinhopetshop.service.Implementation;


import com.example.carrinhopetshop.dto.cart.CartRequest;
import com.example.carrinhopetshop.model.Cart;
import com.example.carrinhopetshop.model.CartItem;
import com.example.carrinhopetshop.model.Category;
import com.example.carrinhopetshop.model.Client;
import com.example.carrinhopetshop.model.Product;
import com.example.carrinhopetshop.repository.CartItemRepository;
import com.example.carrinhopetshop.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {
    @InjectMocks
    private CartItemService itemService;
    @Mock
    private CartItemRepository cartItemRepository;
    private static Category category;
    private static Product product;
    private static Client client;
    private static CartRequest cartRequest;
    @Spy
    private static Cart cart;
    @Spy
    private static CartItem item;

    @BeforeAll
    static void init() {
        category = new Category(1L, "category");
        product = new Product(1L, "name", "description", new BigDecimal("19.90"), category);
        client = new Client(1L, "name");
        cartRequest = new CartRequest(2, product, client);
        cart = new Cart(new BigDecimal("19.90"), client);
        item = new CartItem(cartRequest, cart);
    }

    @Test
    void shouldGetAllCartItems() {
        when(cartItemRepository.findAll()).thenReturn(List.of(item));

        var result = itemService.getAll();

        assertEquals(1, result.size());
        verify(cartItemRepository, times(1)).findAll();
        verifyNoMoreInteractions(cartItemRepository);
    }

    @Test
    void shouldReturnCartItemResponseByGetItemById() {
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        var response = itemService.getById(anyLong());

        assertNotNull(response);
        assertEquals(item.getId(), response.id());
        then(cartItemRepository).should().findById(anyLong());
        then(cartItemRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrownResourceNotFoundExceptionOnGetAnItemThatNotExists() {
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> itemService.getById(anyLong()));
        then(cartItemRepository).should().findById(anyLong());
        then(cartItemRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldSaveACartItem() {
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(item);

        var result = itemService.save(new CartItem());

        assertThat(result).usingRecursiveComparison().isEqualTo(item);
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
        verifyNoMoreInteractions(cartItemRepository);
    }

    @Test
    void shouldRemoveAnItemFromCart() {
        var request = new CartRequest(1, product, client);
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(item);

        var response = itemService.removeItemFromCart(anyLong(), request);

        assertNotNull(response);
        assertEquals(new BigDecimal("19.90"), response.unitPrice());
        assertEquals(1, item.getQuantity());
        then(item).should().decreaseItemQuantity(anyInt());
        then(cartItemRepository).should().save(any(CartItem.class));
        then(cartItemRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrownIllegalArgumentExceptionOnTryToRemoveANegativeQuantityOfItems() {
        var request = new CartRequest(-1, product, client);
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        assertThrows(IllegalArgumentException.class, () -> itemService.removeItemFromCart(anyLong(), request));
    }

    @Test
    void shouldDeleteTheItemIfTheQuantityRequestedToRemoveIsBiggerThanTheActualQuantity() {
        var request = new CartRequest(3, product, client);
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        var response = itemService.removeItemFromCart(anyLong(), request);

        assertNull(response);
        then(cartItemRepository).should().deleteById(anyLong());
        then(cartItemRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldThrownResourceNotFoundExceptionOnTryToRemoveQuantityOfAItemThatNotExists() {
        assertThrows(ResourceNotFoundException.class, () -> itemService.removeItemFromCart(anyLong(), cartRequest));
    }

    @Test
    void shouldDeleteAItemById() {
        when(cartItemRepository.findById(anyLong())).thenReturn(Optional.of(item));

        itemService.deleteItemById(anyLong());

        then(cartItemRepository).should().deleteById(anyLong());
        verifyNoMoreInteractions(cartItemRepository);
    }

    @Test
    void shouldThrownResourceNotFoundExceptionOnDeleteAnItemThatNotExists() {
        assertThrows(ResourceNotFoundException.class, () -> itemService.deleteItemById(anyLong()));
    }

    @Test
    void shouldDeleteCartItemsByCartId() {
        itemService.deleteCartItems(anyLong());

        then(cartItemRepository).should().deleteCartItemsByCart_Id(anyLong());
        verifyNoMoreInteractions(cartItemRepository);
    }

    @Test
    void shouldReturnTrueWhenTheItemIsAlreadyInTheCart() {
        when(cartItemRepository.findCartItemByCart_IdAndProduct_Id(anyLong(), anyLong())).thenReturn(item);

        var response = itemService.productIsAlreadyInTheCart(anyLong(), anyLong());

        assertTrue(response);
        then(cartItemRepository).should().findCartItemByCart_IdAndProduct_Id(anyLong(), anyLong());
        then(cartItemRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldReturnFalseWhenTheItemIsNotInTheCart() {
        when(cartItemRepository.findCartItemByCart_IdAndProduct_Id(anyLong(), anyLong())).thenReturn(null);

        var response = itemService.productIsAlreadyInTheCart(anyLong(), anyLong());

        assertFalse(response);
        then(cartItemRepository).should().findCartItemByCart_IdAndProduct_Id(anyLong(), anyLong());
        then(cartItemRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldUpdateACartItemQuantity() {
        item.setQuantity(1);
        when(cartItemRepository.findCartItemByCart_IdAndProduct_Id(anyLong(), anyLong())).thenReturn(item);

        itemService.updateCartItemQuantity(1, anyLong(), anyLong());

        assertEquals(2, item.getQuantity());
        then(item).should().incrementQuantity(anyInt());
        then(cartItemRepository).should().findCartItemByCart_IdAndProduct_Id(anyLong(), anyLong());
        then(cartItemRepository).shouldHaveNoMoreInteractions();
    }
}