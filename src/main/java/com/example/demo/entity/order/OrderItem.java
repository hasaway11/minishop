package com.example.demo.entity.order;

import com.example.demo.dto.*;
import lombok.*;

import java.time.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderItem {
  private Integer id;
  private Integer orderId;
  private Integer productId;
  private LocalDateTime deliveryAt;
  private LocalDateTime cancelAt;
  private OrderStatus status;
  private String seller;
  private Integer quantity;
  private Integer totalPrice;
  private boolean reviewWritable;

  public OrderItem(Integer orderId, TempOrder tempOrder) {
    this.orderId = orderId;
    this.productId = tempOrder.getProductId();
    this.status = OrderStatus.PAY;
    this.seller = tempOrder.getSeller();
    this.quantity = tempOrder.getQuantity();
    this.totalPrice = tempOrder.getTotalPrice();
    this.reviewWritable = true;
  }
}
