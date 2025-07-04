package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import com.example.demo.service.*;

import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;
import java.util.*;

@RequiredArgsConstructor
@Secured("ROLE_MEMBER")
@RestController
public class OrderController {
  private final OrderService service;

  @PostMapping("/api/orders/prepare")
  public ResponseEntity<Integer> saveSelectedCartItemsToSession(@RequestParam List<Integer> ids, Principal principal) {
    //
    int orderId = service.prepareOrder(ids, principal.getName());
    return ResponseEntity.ok(orderId);
  }

  @GetMapping("/api/orders/check")
  public ResponseEntity<OrderDto.OrderDetail> orderCheck(Integer orderId, Principal principal) {
    OrderDto.OrderDetail orders = service.read(orderId, principal.getName());
    return ResponseEntity.ok(orders);
  }

  @PostMapping("/api/orders")
  public ResponseEntity<Void> order(@Valid OrderDto.OrderRequest dto, BindingResult br, Principal principal) {
    service.order(dto, principal.getName());
    return ResponseEntity.ok(null);
  }

  @GetMapping("/api/orders")
  public ResponseEntity<List<OrderDto.Summary>> orderList(Principal principal) {
    return ResponseEntity.ok(service.orderList(principal.getName()));
  }

  @GetMapping("/api/orders/{orderId}")
  public ResponseEntity<OrderDto.OrderDetail> orderDetails(@PathVariable Integer orderId, Principal principal) {
    return ResponseEntity.ok(service.orderDetails(orderId, principal.getName()));
  }
}
