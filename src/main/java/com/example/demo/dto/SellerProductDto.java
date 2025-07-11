package com.example.demo.dto;

import com.example.demo.entity.product.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.*;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SellerProductDto {
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
  public static class ProductDetail {
    private Integer id;
    private String name;
    private String info;
    private Integer price;
    private Integer salesCount;
    private Integer salesAmount;
    private Double rating;
    private Integer reviewCount;
    private Integer stock;
    private String seller;
    private List<ProductImage> images;
  }

  @Data
  public static class Update {
    @NotNull
    private Integer id;
    @NotEmpty
    private String info;
    @NotNull
    private Integer stock;
  }
}
