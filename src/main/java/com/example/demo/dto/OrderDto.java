package com.example.demo.dto;

import com.example.demo.entity.order.*;
import lombok.*;

import java.time.*;
import java.util.*;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class OrderDto {
  @Data
  public static class OrderList {
    List<Integer> list;
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
  public static class Orders {
    private Integer orderId;
    private LocalDateTime orderDate;
    private DeliveryStatus status;
    private Integer orderTotalPrice;
    private List<Item> orderItems;
  }
}
