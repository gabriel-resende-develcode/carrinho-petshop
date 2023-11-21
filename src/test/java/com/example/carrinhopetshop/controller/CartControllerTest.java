package com.example.carrinhopetshop.controller;

import com.example.carrinhopetshop.dto.cart.CartRequest;
import com.example.carrinhopetshop.dto.cart.CartResponse;
import com.example.carrinhopetshop.model.Category;
import com.example.carrinhopetshop.model.Client;
import com.example.carrinhopetshop.model.Product;
import com.example.carrinhopetshop.service.ICartService;
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
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ICartService cartService;
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
    void shouldReturnCode200WhenGetAllCarts() throws Exception {
        mvc.perform(get("/api/cart", 1))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnCode200WhenGetCartById() throws Exception {
        mvc.perform(get("/api/cart/{cartId}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestOnTryToCreateAnEmptyCart() throws Exception {
        String json = "{}";

        mvc.perform(post("/api/cart")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnCode201WhenCreateCartWithAnItem() throws Exception {
        String json = new ObjectMapper()
                .writeValueAsString(new CartRequest(2, product, client));

        CartResponse mockedCartResponse = new CartResponse(1L, new BigDecimal("39.80"), client, new ArrayList<>());
        when(cartService.createCart(any(CartRequest.class))).thenReturn(mockedCartResponse);

        mvc.perform(post("/api/cart")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/cart/" + mockedCartResponse.id()));
    }

    @Test
    void shouldReturnCode200WhenAddItemToACart() throws Exception {
        String json = new ObjectMapper()
                .writeValueAsString(new CartRequest(2, product, client));

        mvc.perform(put("/api/cart/{cartId}", 1)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNoContentWhenClearACart() throws Exception {
        mvc.perform(delete("/api/cart/{cartId}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnCode200WhenFinalizeAPurchase() throws Exception {
        mvc.perform(put("/api/cart/buy/{cartId}", 1))
                .andExpect(status().isOk());
    }
}