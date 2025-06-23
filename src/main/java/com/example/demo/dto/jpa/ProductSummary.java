package com.example.demo.dto.jpa;

import lombok.*;

@Getter
public class ProductSummary {
  private Integer pno;
  private String seller;
  private String name;
  private Integer price;
  private String image;
  private Integer salesVolume;
  private Double star;

  public ProductSummary(Integer pno, String seller, String name, Integer price, String image, Integer salesVolume, Integer totalStars, Integer reviewCount) {
    this.pno = pno;
    this.seller = seller;
    this.name = name;
    this.price = price;
    this.image = image;
    this.salesVolume = salesVolume;
    this.star = reviewCount==0? 0.0 : (double)totalStars/reviewCount;
  }
}
