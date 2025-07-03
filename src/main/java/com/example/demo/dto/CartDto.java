package com.example.demo.dto;

import lombok.*;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartDto {
  @Data
  public static class Summary {
    private Integer id;
    private String image;
    private String name;
    private Integer quantity;
    private Integer totalPrice;
  }

  @Data
  @AllArgsConstructor
  public static class Carts {
    private List<Summary> items;
    private int cartTotalPrice;
  }

  @Data
  public static class CheckoutDto {
    private Integer productId;
    private String username;
    private Integer quantity;
    private Integer totalPrice;
  }
}
