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
  public ResponseEntity<List<SellerOrderDto.OrderSummary>> findBySeller(Principal principal) {
    return ResponseEntity.ok(service.findBySeller(principal.getName()));
  }

  @PutMapping("/api/sellers/orders")
  public ResponseEntity<List<SellerOrderDto.OrderSummary>> update(@RequestParam List<Integer> ids, Principal principal) {
    List<SellerOrderDto.OrderSummary> list = service.updateStatus(ids, principal.getName());
    return ResponseEntity.ok(list);
  }
}
