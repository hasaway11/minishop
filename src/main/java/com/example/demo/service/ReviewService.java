package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import com.example.demo.entity.product.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ReviewService {
  private final ReviewMapper reviewDao;
  private final OrderItemMapper orderItemDao;
  public ResponseEntity<List<OrderItem>> getReviewableOrderItems(String loginId) {
    return ResponseEntity.ok(orderItemDao.findWritableList(loginId));
  }

  public ReviewDto.ReviewList write(ReviewDto.Create dto, String loginId) {
    return null;
  }
}
