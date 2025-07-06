package com.example.demo.entity.order;

import lombok.*;

import java.time.*;

@Getter
@NoArgsConstructor
public class Order {
  private Integer id;
  private String username;
  private LocalDateTime orderAt;
  private LocalDateTime deliveryAt;
  private LocalDateTime cancelAt;
  private String zipcode;
  private String address;
  private OrderStatus status;
  private Integer orderTotalPrice;

  public Order(String username, String zipcode, String deliveryAddress, int orderTotalPrice) {
    this.username = username;
    this.orderAt = LocalDateTime.now();
    this.zipcode = zipcode;
    this.address = deliveryAddress;
    this.status = OrderStatus.PAY;
    this.orderTotalPrice = orderTotalPrice;
  }
}
