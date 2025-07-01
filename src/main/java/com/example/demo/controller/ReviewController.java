package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import com.example.demo.service.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;
import java.util.*;

@RequiredArgsConstructor
@Secured("ROLE_USER")
@RestController
@Validated
public class ReviewController {
  private final ReviewService service;

  // 리뷰 작성 가능 목록
  @GetMapping("/reviews/available")
  public ResponseEntity<List<OrderItem>> readListOfProductsAvailableForReview(Principal principal) {
    // 주문번호, 주문일, 상품번호, 이미지
    return service.getReviewableOrderItems(principal.getName());
  }

  // 리뷰 추가 api
  @PostMapping("/reviews/new")
  public ResponseEntity<ReviewDto.ReviewList> write(@Valid ReviewDto.Create dto, Principal principal) {
    return ResponseEntity.ok(service.write(dto, principal.getName()));
  }
}
