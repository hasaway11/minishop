package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import com.example.demo.entity.product.*;
import com.example.demo.exception.*;
import com.example.demo.util.*;
import lombok.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ReviewService {
  private final ReviewMapper reviewDao;
  private final OrderItemMapper orderItemDao;
  private final ProductMapper productDao;

  @Transactional
  public boolean write(ReviewDto.Create dto, String loginId) {
    OrderItem item =  orderItemDao.findById(dto.getOrderItemId()).orElseThrow(()->new EntityNotFoundException("주문내역이 없습니다"));
    if(!item.isReviewWritable())
      throw new EntityNotFoundException("이미 리뷰를 작성했습니다");
    Review review = dto.toEntity(loginId);
    reviewDao.save(review);
    orderItemDao.updateReviewableToFalse(dto.getOrderItemId());
    return productDao.updateRating(dto.getProductId(), 1, dto.getRating())==1;
  }

  public OrderDto.OrderProductDto getPrdocutSummaryForReview(Integer orderItemId) {
    return orderItemDao.findProductByOrderItemId(orderItemId, MiniShopConstants.IMAGE_URL).orElseThrow(()->new EntityNotFoundException("주문상품을 찾을 수 없습니다"));
  }

  public List<ReviewDto.Read> findByWriter(String loginId) {
    return reviewDao.findByWriter(loginId, MiniShopConstants.IMAGE_URL);
  }

  public List<Review> delete(int reviewId, String loginId) {
    Review review = reviewDao.findById(reviewId).orElseThrow(()->new EntityNotFoundException("리뷰를 찾을 수 없습니다"));
    if(!review.getWriter().equals(loginId))
      throw new JobFailException("잘못된 작업입니다");
    reviewDao.deleteById(reviewId);
    productDao.updateRating(review.getProductId(), -1, -review.getRating());
    return reviewDao.findByProductId(review.getProductId());
  }
}
