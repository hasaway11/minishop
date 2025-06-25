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
    private OrderStatus status;
    private Integer orderTotalPrice;
    private List<OrderDto.Item> orderItems;

    public Orders(Order order, List<OrderDto.Item> orderItems) {
      this.orderId = order.getOrderId();
      this.orderDate = order.getOrderDate();
      this.status = order.getStatus();
      this.orderTotalPrice = order.getOrderTotalPrice();
      this.orderItems = orderItems;
    }
  }
}
