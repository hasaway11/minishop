package com.example.demo.dto.jpa;

import lombok.*;

@Getter
@AllArgsConstructor
public class CartItemSummary {
  private Integer pno;
  private String name;
  private Integer quantity;
  private Integer totalPrice;
  private String image;
}
