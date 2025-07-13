package com.example.demo.entity.order;

import lombok.*;

import java.time.*;

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
  private String seller;
  private LocalDateTime invalidAt = LocalDateTime.now().plusDays(1);
}
