package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;
import java.util.*;

@Secured("ROLE_SELLER")
@RequiredArgsConstructor
@RestController
public class SellerOrderController {
  private final SellerOrderService service;

  @GetMapping("/api/sellers/orders")
  public ResponseEntity<List<SellerOrderDto.OrderDetail>> findBySeller(Principal principal) {
    return ResponseEntity.ok(service.findBySeller(principal.getName()));
  }

  @PutMapping("/api/sellers/orders")
  public ResponseEntity<Void> updateStaus(@RequestBody List<SellerOrderDto.OrderItemId> list) {
    service.updateStatus(list);
    return ResponseEntity.ok(null);
  }
}
