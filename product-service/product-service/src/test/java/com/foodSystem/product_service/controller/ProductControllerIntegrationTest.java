package com.foodSystem.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodSystem.product_service.entity.Category;
import com.foodSystem.product_service.entity.Menu;
import com.foodSystem.product_service.entity.Product;
import com.foodSystem.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper; // For JSON serialization

    @BeforeEach
    public void setUp() {
        // Clear the database before each test
        productRepository.deleteAll();

        // Seed some test data
        Menu menu = new Menu();
        menu.setName("Test Menu");

        productRepository.save(Product.builder()
                .name("Margherita Pizza")
                .price(9.99)
                .description("Classic pizza")
                .available(true)
                .menu(menu)
                .categories(Arrays.asList(new Category("Vegetarian")))
                .build());

        productRepository.save(Product.builder()
                .name("Pepperoni Pizza")
                .price(11.99)
                .description("Spicy pizza")
                .available(false)
                .menu(menu)
                .categories(Arrays.asList(new Category("Meat")))
                .build());
    }

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/products?page=0&size=10&sortBy=name&sortDir=asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", is("Margherita Pizza")))
                .andExpect(jsonPath("$.content[1].name", is("Pepperoni Pizza")));
    }

    @Test
    public void testGetProductById() throws Exception {
        Long id = productRepository.findAll().get(0).getId();

        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Margherita Pizza")))
                .andExpect(jsonPath("$.price", is(9.99)));
    }

    @Test
    public void testGetProductById_NotFound() throws Exception {
        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product newProduct = Product.builder()
                .name("Cheese Pizza")
                .price(8.99)
                .description("Simple cheese pizza")
                .available(true)
                .build();

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name", is("Cheese Pizza")))
                .andExpect(jsonPath("$.price", is(8.99)));
    }

    @Test
    public void testCreateProduct_DuplicateName() throws Exception {
        Product duplicateProduct = Product.builder()
                .name("Margherita Pizza") // Already exists
                .price(10.00)
                .available(true)
                .build();

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateProduct)))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("Product already exists")));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Long id = productRepository.findAll().get(0).getId();
        Product updatedProduct = Product.builder()
                .name("Updated Margherita")
                .price(12.99)
                .description("Updated description")
                .available(false)
                .build();

        mockMvc.perform(put("/api/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Margherita")))
                .andExpect(jsonPath("$.price", is(12.99)))
                .andExpect(jsonPath("$.description", is("Updated description")))
                .andExpect(jsonPath("$.available", is(false)));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Long id = productRepository.findAll().get(0).getId();

        mockMvc.perform(delete("/api/products/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetProductsByName() throws Exception {
        mockMvc.perform(get("/api/products/filterByName?name=Margherita&page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("Margherita Pizza")));
    }

    @Test
    public void testGetProductsByPriceRange() throws Exception {
        mockMvc.perform(get("/api/products/filterByPrice?minPrice=9.0&maxPrice=10.0&page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("Margherita Pizza")));
    }

    @Test
    public void testGetProductsByAvailability() throws Exception {
        mockMvc.perform(get("/api/products/filterByAvailability?available=true&page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("Margherita Pizza")));
    }

    @Test
    public void testSearchProducts() throws Exception {
        mockMvc.perform(get("/api/products/search")
                        .param("name", "Pizza")
                        .param("minPrice", "5.0")
                        .param("maxPrice", "15.0")
                        .param("available", "true")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "price")
                        .param("sortDir", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("Margherita Pizza")));
    }
}