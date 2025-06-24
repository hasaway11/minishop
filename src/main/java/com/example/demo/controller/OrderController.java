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
  public ResponseEntity<Void> saveSelectedCartItemsToSession(OrderDto.OrderList dto, Principal principal, HttpSession session) {
    List<CartDto.Summary> items = service.toOrderItem(dto.getList(), principal.getName());
    session.setAttribute("items", items);
    return ResponseEntity.ok(null);
  }

  @GetMapping("/api/orders/prepare")
  public ResponseEntity<List<CartDto.Summary>> orderCheck(HttpSession session) {
    if(session.getAttribute("item")==null)
      throw new JobFailException("작업을 수행할 수 없습니다");
    List<CartDto.Summary> items = (List<CartDto.Summary>)session.getAttribute("item");
    return ResponseEntity.ok(items);
  }

  @PostMapping("/api/orders")
  public ResponseEntity<Void> order(HttpSession session, String address, Principal principal) {
    if(session.getAttribute("item")==null)
      throw new JobFailException("작업을 수행할 수 없습니다");
    List<CartDto.Summary> items = (List<CartDto.Summary>)session.getAttribute("item");
    service.order(items, address, principal.getName());
    session.removeAttribute("item");
    return ResponseEntity.ok(null);
  }

  @GetMapping("/api/orders")
  public ResponseEntity<List<Order>> orderList(Principal principal) {
    return ResponseEntity.ok(service.orderList(principal.getName()));
  }

  @GetMapping("/api/orders/{orderId}")
  public ResponseEntity<?> orderDetails(@PathVariable Integer orderId, Principal principal) {
    service.orderDetails(orderId, principal.getName());
    return null;
  }
}
