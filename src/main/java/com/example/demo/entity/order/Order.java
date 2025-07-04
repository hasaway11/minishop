package com.example.demo.entity.order;

import lombok.*;

import java.time.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
  private Integer id;
  private String username;
  private LocalDateTime orderAt;
  private LocalDateTime paidAt;
  private LocalDateTime cancelAt;
  private String deliveryAddress;
  private OrderStatus status;
  private Integer orderTotalPrice;
}
