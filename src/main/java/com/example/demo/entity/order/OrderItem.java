package com.example.demo.entity.order;

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
}
