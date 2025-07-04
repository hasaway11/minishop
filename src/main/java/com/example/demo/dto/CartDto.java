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
  public static class CheckOutItem {
    private Integer id;
    private String name;
    private String image;
    private Integer quantity;
    private Integer totalPrice;
    private String username;
  }

  @Data
  @AllArgsConstructor
  public static class CheckOut {
    private Integer orderTotalPrice;
    private List<CartDto.CheckOutItem> items;
  }
}
