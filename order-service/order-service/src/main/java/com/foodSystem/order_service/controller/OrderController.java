package com.foodSystem.order_service.controller;

import com.foodSystem.order_service.entity.Order;
import com.foodSystem.order_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order Controller", description = "API endpoints for managing orders")
public class OrderController {

  @Autowired private OrderService orderService;

  @Operation(summary = "Get all orders", description = "Retrieves a list of all orders")
  @GetMapping
  public List<Order> getAllOrders() {
    return orderService.getAllOrders();
  }

  @Operation(summary = "Get order by ID", description = "Retrieves a specific order by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order found"),
      @ApiResponse(responseCode = "404", description = "Order not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(
      @Parameter(description = "Order ID", required = true) @PathVariable Long id) {
    return orderService
        .getOrderById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(summary = "Create a new order", description = "Creates a new order")
  @PostMapping
  public Order createOrder(@RequestBody Order order) {
    return orderService.createOrder(order);
  }

  @Operation(summary = "Update an order", description = "Updates an existing order by ID")
  @PutMapping("/{id}")
  public ResponseEntity<Order> updateOrder(
      @Parameter(description = "Order ID", required = true) @PathVariable Long id, 
      @RequestBody Order orderDetails) {
    System.out.println("order details: " + orderDetails);
    return ResponseEntity.ok(orderService.updateOrder(id, orderDetails));
  }

  @Operation(summary = "Delete an order", description = "Deletes an order by ID")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(
      @Parameter(description = "Order ID", required = true) @PathVariable Long id) {
    orderService.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Get paginated orders", description = "Retrieves orders with pagination and sorting")
  @GetMapping("/page")
  public Page<Order> getOrders(
      Pageable pageable, 
      @Parameter(description = "Field to sort by", example = "id") @RequestParam(defaultValue = "id") String sortBy) {
    Sort sort = Sort.by(sortBy);
    return orderService.getOrders(pageable, sort);
  }

  @Operation(summary = "Filter orders", description = "Filters orders by status, userId, or productId")
  @GetMapping("/filter")
  public Page<Order> getFilteredOrders(
          Pageable pageable,
          @Parameter(description = "Order status") @RequestParam(required = false) String status,
          @Parameter(description = "User ID") @RequestParam(required = false) Long userId,
          @Parameter(description = "Product ID") @RequestParam(required = false) Long productId) {
    return orderService.getFilteredOrders(pageable, status, userId, productId);
  }

  @Operation(summary = "Search orders", description = "Searches orders by keyword")
  @GetMapping("/search")
  public Page<Order> searchOrders(
      Pageable pageable, 
      @Parameter(description = "Search keyword", required = true) @RequestParam String keyword) {
    return orderService.searchOrders(pageable, keyword);
  }
}
