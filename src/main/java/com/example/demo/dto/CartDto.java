package com.example.demo.dto;

import com.example.demo.dto.jpa.*;
import com.example.demo.entity.order.*;
import lombok.*;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartDto {
  @Data
  @AllArgsConstructor
  public static class Carts {
    private List<CartItemSummary> items;
    private int cartTotalPrice;
  }
}
