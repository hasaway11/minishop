package com.example.demo.entity.order;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TempOrder {
  private int tempId;
  private int cartItemId;
  private String username;
  private int productId;
  private String name;
  private String image;
  private int quantity;
  private int totalPrice;
}
