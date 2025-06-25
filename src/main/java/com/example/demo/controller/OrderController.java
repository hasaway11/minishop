package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import com.example.demo.exception.*;
import com.example.demo.service.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;
import java.util.*;

@RequiredArgsConstructor
@Secured("ROLE_USER")
@RestController
public class OrderController {
  private final OrderService service;

  // 장바구니에서 사용자가 선택한 번호를 입력받아 주문 확인 화면으로 이동시킨다
  // 주문 확인 화면은 사용자가 선택한 상품 목록을 확인받고 배송지를 입력하는 화면으로 주문 정보를 수정할 수는 없다
  // (주문 정보는 세션에 저장)
  @PostMapping("/api/orders/prepare")
  public ResponseEntity<Integer> saveSelectedCartItemsToSession(OrderDto.OrderList dto, Principal principal) {
    // 주문과 주문상세를 생성해서 db에 저장 후 주문 번호를 리턴
    int orderId = service.prepareOrder(dto.getList(), principal.getName());
    return ResponseEntity.ok(orderId);
  }

  @GetMapping("/api/orders/check")
  public ResponseEntity<OrderDto.Orders> orderCheck(Integer orderId, Principal principal) {
    OrderDto.Orders orders = service.read(orderId, principal.getName());
    return ResponseEntity.ok(orders);
  }

  @PostMapping("/api/orders")
  public ResponseEntity<Void> order(Integer orderId, String address, Principal principal) {
    service.order(orderId, address, principal.getName());
    return ResponseEntity.ok(null);
  }

  @GetMapping("/api/orders")
  public ResponseEntity<List<Order>> orderList(Principal principal) {
    return ResponseEntity.ok(service.orderList(principal.getName()));
  }

  @GetMapping("/api/orders/{orderId}")
  public ResponseEntity<OrderDto.Orders> orderDetails(@PathVariable Integer orderId, Principal principal) {
    return ResponseEntity.ok(service.orderDetails(orderId, principal.getName()));
  }
}
