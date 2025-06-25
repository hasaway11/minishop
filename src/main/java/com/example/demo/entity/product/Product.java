package com.example.demo.entity.product;

import com.example.demo.dto.*;

import com.example.demo.exception.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  private Integer productId;
  private String seller;
  private String name;
  private String info;
  private Integer price;
  private Integer salesVolume;
  private Integer salesAmount;
  private Integer totalStars;
  private Integer reviewCount;
  private Integer stock;
  private Integer category;

  public ProductDto.Read toRead(List<String> images) {
    return new ProductDto.Read(productId, name, info, price, (double)totalStars/reviewCount, reviewCount, stock, images);
  }

  public void checkSellerOrThorw(String loginId) {
    if(!seller.equals(loginId))
      throw new JobFailException("작업을 수행할 수 없습니다");
  }
}
