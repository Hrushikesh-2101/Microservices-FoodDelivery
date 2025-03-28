package com.foodSystem.order_service.controller;

import com.foodSystem.order_service.entity.Order;
import com.foodSystem.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

  @Autowired private OrderService orderService;

  @GetMapping
  public List<Order> getAllOrders() {
    return orderService.getAllOrders();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    return orderService
        .getOrderById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Order createOrder(@RequestBody Order order) {
    return orderService.createOrder(order);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
    System.out.println("order details: " + orderDetails);
    return ResponseEntity.ok(orderService.updateOrder(id, orderDetails));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    orderService.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/page")
  public Page<Order> getOrders(
      Pageable pageable, @RequestParam(defaultValue = "id") String sortBy) {
    Sort sort = Sort.by(sortBy);
    return orderService.getOrders(pageable, sort);
  }

  @GetMapping("/filter")
  public Page<Order> getFilteredOrders(
          Pageable pageable,
          @RequestParam(required = false) String status,
          @RequestParam(required = false) Long userId,
          @RequestParam(required = false) Long productId) {
    return orderService.getFilteredOrders(pageable, status, userId, productId);
  }

  @GetMapping("/search")
  public Page<Order> searchOrders(Pageable pageable, @RequestParam String keyword) {
    return orderService.searchOrders(pageable, keyword);
  }
}
