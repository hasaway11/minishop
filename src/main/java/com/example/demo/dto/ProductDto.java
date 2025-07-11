package com.example.demo.dto;

import com.example.demo.entity.product.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.*;

import java.util.*;

@NoArgsConstructor(access = AccessLevel. PRIVATE)
public class ProductDto {
  @Data
  public static class Update {
    @NotNull
    private Integer productId;
    @NotEmpty
    private String info;
    @NotNull
    private Integer stock;
  }

  @Data
  public static class Summary {
    private Integer id;
    private String seller;
    private String name;
    private String image;
    private Integer price;
    private Integer salesVolume;
    private Double star;
    private Integer reviewCount;
  }

  @Data
  public static class SellerSummary {
    private Integer id;
    private boolean orderExist;
    private String name;
    private String image;
    private Integer price;
    private Integer salesVolume;
    private Double star;
    private Integer reviewCount;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  public static class Read {
    private Integer id;
    private String name;
    private String info;
    private Integer price;
    private Integer reviewCount;
    private Integer stock;
    private String seller;
    private Double rating;
    private List<String> images;
    private List<Review> reviews;

    public Read setImages(List<String> images) {
      this.images = images;
      return this;
    }
  }

  @Data
  public static class Seller {
    private Integer pageno =1;
    private String seller;
  }
}
