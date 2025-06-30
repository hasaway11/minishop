package com.example.demo.entity.product;

import lombok.*;

@Getter
@NoArgsConstructor
public class Category {
  private Integer categoryId;
  private String name;
  private Integer parent;
}
