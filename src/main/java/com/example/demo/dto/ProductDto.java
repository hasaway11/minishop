package com.example.demo.dto;

import com.example.demo.entity.product.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.*;

@NoArgsConstructor(access = AccessLevel. PRIVATE)
public class ProductDto {
  @Data
  public static class Create {
    @NotEmpty
    private String name;
    @NotEmpty
    private String info;
    private MultipartFile image;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;
    @NotNull
    private Integer category;
    private MultipartFile image;

    public Product toEntity(String seller) {
      return new Product(0, seller, name, info, null, price, 0, 0, 0, 0, stock, category);
    }
  }

  @Data
  public static class Update {
    @NotNull
    private Integer productId;
    @NotEmpty
    private String info;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;
  }

  @Data
  public static class Summary {
    private Integer productId;
    private String seller;
    private String name;
    private String image;
    private Integer salesVolume;
    private Double star;
    private Integer reviewCount;
  }

  @AllArgsConstructor
  @Data
  public static class Read {
    private Integer productId;
    @Column(length=20)
    private String name;
    @Lob
    private String info;
    @Lob
    private String image;
    private Integer price;
    private Double star;
    private Integer reviewCount;
    private Integer stock;
  }
}
