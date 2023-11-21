package com.example.carrinhopetshop.controller;

import com.example.carrinhopetshop.dto.cart.CartRequest;
import com.example.carrinhopetshop.dto.cartItem.CartItemResponse;
import com.example.carrinhopetshop.model.Category;
import com.example.carrinhopetshop.model.Client;
import com.example.carrinhopetshop.model.Product;
import com.example.carrinhopetshop.service.ICartItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ICartItemService cartItemService;

    private static Product product;
    private static Category category;
    private static Client client;

    @BeforeAll
    static void init() {
        category = new Category(1L, "Briquedos");
        product = new Product(1L, "Bolinha", "Bolinha para pets", new BigDecimal("19.90"), category);
        client = new Client(1L, "João");
    }

    @Test
    void shouldReturnCode200WhenGetAllItems() throws Exception {
        mvc.perform(get("/api/cart/items"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnCode200WhenGetItemById() throws Exception {
        mvc.perform(get("/api/cart/items/{itemId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnCode200WhenRemoveAQuantityOfACartItem() throws Exception {
        String json = new ObjectMapper()
                .writeValueAsString(new CartRequest(1, product, client));

        var response = new CartItemResponse(1L, new BigDecimal("19.90"), 2, product);
        when(cartItemService.removeItemFromCart(anyLong(), any(CartRequest.class))).thenReturn(response);

        mvc.perform(put("/api/cart/items/{itemId}", 1)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNoContentWhenRemoveAQuantityBiggerThanActual() throws Exception {
        String json = new ObjectMapper()
                .writeValueAsString(new CartRequest(2, product, client));

        when(cartItemService.removeItemFromCart(anyLong(), any(CartRequest.class))).thenReturn(null);

        mvc.perform(put("/api/cart/items/{itemId}", 1)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}