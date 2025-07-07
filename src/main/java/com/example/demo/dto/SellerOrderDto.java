package com.example.demo.dto;

import com.example.demo.entity.order.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.*;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class SellerOrderDto {
  @Data
  public static class OrderDetail {
    @JsonFormat(pattern="M월 d일(E) HH:mm", locale="ko", timezone="Asia/Seoul")
    private LocalDateTime orderAt;
    private String image;
    private String productName;
    private String productPrice;
    private String orderId;
    private String orderer;
    private OrderStatus status;
    private Integer productId;
    private Integer quantity;
    private Integer totalPrice;
  }

  @Data
  public static class OrderItemId {
    private Integer id;
    private Integer productId;
  }
}
