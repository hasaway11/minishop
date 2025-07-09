package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import com.example.demo.entity.product.*;
import com.example.demo.service.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.validation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;
import java.util.*;

@RequiredArgsConstructor
@Secured("ROLE_MEMBER")
@RestController
@Validated
public class ReviewController {
  private final ReviewService service;

  @GetMapping("/reviews/product")
  public ResponseEntity<OrderDto.OrderProductDto> getPrdocutSummaryForReview(Integer orderItemId) {
    return ResponseEntity.ok(service.getPrdocutSummaryForReview(orderItemId));
  }

  // 리뷰 추가 api
  @PostMapping("/reviews/new")
  public ResponseEntity<Void> write(@Valid ReviewDto.Create dto, Principal principal) {
    boolean result = service.write(dto, principal.getName());
    if(result)
      return ResponseEntity.ok(null);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
  }

  // 제품번호 추가해서 응답하기
  @DeleteMapping("/api/reviews/{id}")
  public ResponseEntity<List<Review>> delete(@PathVariable("id") int reviewId, Principal principal) {
    return ResponseEntity.ok(service.delete(reviewId, principal.getName()));
  }

  @GetMapping("/reviews")
  public ResponseEntity<List<ReviewDto.Read>> readReviews(Principal principal) {
    return ResponseEntity.ok(service.findByWriter(principal.getName()));
  }
}
