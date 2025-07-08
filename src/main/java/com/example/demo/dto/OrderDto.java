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
    private Integer id;
    private Integer productId;
    private String productName;
    private String image;
    private Integer quantity;
    private Integer totalPrice;
    @JsonFormat(pattern="yyyy년 M월 d일(E) HH:mm", locale="ko", timezone="Asia/Seoul")
    private LocalDateTime deliveryAt;
    private OrderStatus status;
    private boolean reviewWritable;
  }

  @Data
  public static class OrderDetail {
    private String address;
    @JsonFormat(pattern="yyyy년 M월 d일(E) HH:mm", locale="ko", timezone="Asia/Seoul")
    private LocalDateTime orderAt;
    private Integer orderTotalPrice;
    @Setter
    private List<OrderDto.Item> orderItems;
  }

  @Data
  public static class Summary {
    private Integer id;
    @JsonFormat(pattern="M월 d일(E) HH:mm", locale="ko", timezone="Asia/Seoul")
    private LocalDateTime orderAt;
    private int orderTotalPrice;
    private int count;
    private String productName;
  }

  @Data
  public static class OrderProductDto {
    private Integer productId;
    private String productName;
    private String image;
  }
}
