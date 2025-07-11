package com.example.demo.dto;

import com.example.demo.entity.order.*;
import com.example.demo.entity.product.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.*;
import java.util.*;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class SellerOrderDto {
  @Data
  public static class OrderSummary {
    private Integer id;
    private String productName;
    private String address;
    @JsonFormat(pattern="M월 d일(E) HH:mm", locale="ko", timezone="Asia/Seoul")
    private LocalDateTime orderAt;
    private String orderer;
    private OrderStatus status;
    private Integer productId;
    private Integer quantity;
    private Integer totalPrice;
  }
}
