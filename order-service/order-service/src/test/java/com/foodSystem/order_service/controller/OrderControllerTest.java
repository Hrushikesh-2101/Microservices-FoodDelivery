package com.foodSystem.order_service.controller;

import com.foodSystem.order_service.entity.Order;
import com.foodSystem.order_service.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        orderRepository.deleteAll();
        Order order1 = new Order(1L, 1L, "completed");
        Order order2 = new Order(2L, 2L, "pending");
        orderRepository.saveAll(Arrays.asList(order1, order2));
    }

    @Test
    public void testGetAllOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("completed"))
                .andExpect(jsonPath("$[1].status").value("pending"));
    }

    @Test
    public void testGetOrderById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("completed"));
    }

    @Test
    public void testCreateOrder() throws Exception {
        String newOrder = "{\"userId\":3,\"productId\":3,\"status\":\"shipped\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newOrder))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("shipped"));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        String updatedOrder = "{\"userId\":1,\"productId\":1,\"status\":\"shipped\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedOrder))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("shipped"));
    }

    @Test
    public void testDeleteOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetOrdersWithSorting() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/page?sortBy=status")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].status").value("completed"))
                .andExpect(jsonPath("$.content[1].status").value("pending"));
    }

    @Test
    public void testGetFilteredOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/filter?status=completed")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].status").value("completed"));
    }

    @Test
    public void testSearchOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/search?keyword=completed")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].status").value("completed"));
    }
}