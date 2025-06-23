package com.example.demo.entity.product;

import com.example.demo.dto.*;

import com.example.demo.exception.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  private Integer productId;
  private String seller;
  private String name;
  private String info;
  private String image;
  private Integer price;
  private Integer salesVolume;
  private Integer salesAmount;
  private Integer totalStars;
  private Integer reviewCount;
  private Integer stock;
  private Integer category;

  public ProductDto.Read toRead() {
    return new ProductDto.Read(productId, name, info, image, price, (double)totalStars/reviewCount, reviewCount, stock);
  }

  public void checkSellerOrThorw(String loginId) {
    if(!seller.equals(loginId))
      throw new JobFailException("작업을 수행할 수 없습니다");
  }
}
