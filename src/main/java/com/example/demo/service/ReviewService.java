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
    orderItemDao.findWritableList(loginId);
    List<OrderItem> ois = orderDao.findByUsername(loginId).stream().flatMap(order->order.getOrderItemList().stream()).filter(oi->!oi.getReviewWritingStatus()).toList();
    return ResponseEntity.ok(ois);
  }

  public ReviewDto.ReviewList write(ReviewDto.Create dto, String loginId) {
    Product product = productDao.findById(dto.getPno()).orElseThrow(ProductNotFoundException::new);
    reviewDao.save(dto.toReview(loginId));
    product.updateRating(reviewDao.averageOfStarByPno(dto.getPno()));
    return getReviewListDto(dto.getPno());
  }
}
