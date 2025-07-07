package com.example.demo.entity.order;

import lombok.*;

import java.time.*;

@Getter
@NoArgsConstructor
public class Order {
  private Integer id;
  private String orderer;
  private LocalDateTime orderAt;
  private String zipcode;
  private String address;
  private Integer orderTotalPrice;

  public Order(String orderer, String zipcode, String deliveryAddress, int orderTotalPrice) {
    this.orderer = orderer;
    this.orderAt = LocalDateTime.now();
    this.zipcode = zipcode;
    this.address = deliveryAddress;
    this.orderTotalPrice = orderTotalPrice;
  }
}
