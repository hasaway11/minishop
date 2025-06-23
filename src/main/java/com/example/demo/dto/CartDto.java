package com.example.demo.dto;

import com.example.demo.dto.jpa.*;
import com.example.demo.entity.order.*;
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
}
