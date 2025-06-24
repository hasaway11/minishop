package com.example.demo.dto;

import lombok.*;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartDto {
  @Data
  public static class Summary {
    private Integer productId;
    private String name;
    private Integer quantity;
    private Integer totalPrice;
    private String image;
  }

  @Data
  @AllArgsConstructor
  public static class Carts {
    private List<Summary> items;
    private int cartTotalPrice;
  }

  @Data
  public static class Delete {
    private List<Integer> ids;
  }
}
