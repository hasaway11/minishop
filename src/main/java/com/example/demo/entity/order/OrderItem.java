package com.example.demo.entity.order;

import com.example.demo.dto.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItem {
  private Integer orderId;
  private Integer productId;
  private Integer quantity;
  private Integer totalPrice;
  private boolean reviewWritable;

  public OrderItem(Integer orderId, CartDto.CheckoutDto dto) {
    this.orderId = orderId;
    this.productId = dto.getProductId();
    this.quantity = dto.getQuantity();
    this.totalPrice = dto.getTotalPrice();
    this.reviewWritable = false;
  }
}
