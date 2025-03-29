package com.foodSystem.product_service.controller;

import com.foodSystem.product_service.entity.Product;
import com.foodSystem.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.is;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(10.0);
        product.setDescription("Test Description");
        product.setAvailable(true);
        productRepository.save(product);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "name")
                .param("sortDir", "asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("Test Product")));
    }

    @Test
    public void testGetProductById() throws Exception {
        Optional<Product> product = productRepository.findByNameContaining("Test Product").stream().findFirst();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/" + product.get().getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Product")));
    }

    @Test
    public void testCreateProduct() throws Exception {
        String newProductJson = "{\"name\":\"New Product\",\"price\":20.0,\"description\":\"New Description\",\"available\":true}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newProductJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("New Product")));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Optional<Product> product = productRepository.findByNameContaining("Test Product").stream().findFirst();
        String updatedProductJson = "{\"name\":\"Updated Product\",\"price\":30.0,\"description\":\"Updated Description\",\"available\":false}";
        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/" + product.get().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Product")));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Optional<Product> product = productRepository.findByNameContaining("Test Product").stream().findFirst();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/" + product.get().getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}