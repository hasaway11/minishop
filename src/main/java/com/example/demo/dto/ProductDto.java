package com.example.demo.dto;

import com.example.demo.entity.product.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.*;

import java.util.*;

@NoArgsConstructor(access = AccessLevel. PRIVATE)
public class ProductDto {
  @Data
  public static class Create {
    @NotEmpty
    private String name;
    @NotEmpty
    private String info;
    @NotEmpty
    private List<MultipartFile> images;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;
    @NotNull
    private Integer category;

    public Product toEntity(String seller) {
      return new Product(0, seller, name, info, price, 0, 0, 0, 0, stock, category);
    }
  }

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
