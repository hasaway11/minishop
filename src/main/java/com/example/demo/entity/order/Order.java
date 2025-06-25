package com.example.demo.entity.order;

import lombok.*;

import java.time.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
  private Integer orderId;
  private String username;
  private LocalDateTime orderDate;
  private String deliveryAddress;
  private OrderStatus status;
  private Integer orderTotalPrice;
}
