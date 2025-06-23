package com.example.demo.entity.order;

import lombok.*;

@Getter
public class OrderItem {
  private Integer orderId;
  private Integer orderItemId;
  private Integer productId;
  private Integer quantity;
  private Integer totalPrice;
}
