package com.example.demo.dto;

import com.example.demo.entity.order.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.*;
import java.util.*;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class OrderDto {
  @Data
  public static class OrderRequest {
    private Integer id;
    private String zipcode;
    private String address;
  }

  @Data
  public static class Item {
    private Integer productId;
    private String name;
    private String image;
    private Integer quantity;
    private Integer totalPrice;
  }

  @Data
  public static class OrderDetail {
    private String address;
    @JsonFormat(pattern="yyyy년 M월 d일(E) HH:mm", locale="ko", timezone="Asia/Seoul")
    private LocalDateTime orderAt;
    @JsonFormat(pattern="yyyy년 M월 d일(E) HH:mm", locale="ko", timezone="Asia/Seoul")
    private LocalDateTime deliveryAt;
    private OrderStatus status;
    private Integer orderTotalPrice;
    @Setter
    private List<OrderDto.Item> orderItems;
  }

  @Data
  public static class Summary {
    private Integer id;
    @JsonFormat(pattern="M월 d일(E) HH:mm", locale="ko", timezone="Asia/Seoul")
    private LocalDateTime orderTime;
    private OrderStatus status;
    private int count;
    private String name;
  }
}
