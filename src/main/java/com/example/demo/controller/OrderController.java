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

  // 장바구니에서 선택한 상품들을 임시 테이블에 저장
  @PostMapping("/api/orders/prepare")
  public ResponseEntity<Integer> saveSelectedCartItems(@RequestParam List<Integer> ids, Principal principal) {
    int orderId = service.prepareOrder(ids, principal.getName());
    return ResponseEntity.ok(orderId);
  }

  // 주문하기 전, 주문 내용을 확인하기 위해 출력
  @GetMapping("/api/orders/check")
  public ResponseEntity<Map<String,Object>> orderCheck(Integer orderId, Principal principal) {
    Map<String,Object> map = service.read(orderId, principal.getName());
    return ResponseEntity.ok(map);
  }

  // 주문 내용 확인 후 주문
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
