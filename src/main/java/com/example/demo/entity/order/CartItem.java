package com.example.demo.entity.order;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
  private Integer id;
  private String username;
  private Integer productId;
  private Integer quantity;
}
