package com.example.demo.entity.product;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
  private Integer imageId;
  private String name;
  private Integer productId;
}
