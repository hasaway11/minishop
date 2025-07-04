package com.example.demo.entity.order;

import lombok.*;

@Data
@AllArgsConstructor
public class TempOrder {
  private int id;
  private int cartItemId;
  private String username;
  private int productId;
  private String name;
  private String image;
  private int quantity;
  private int totalPrice;
}
