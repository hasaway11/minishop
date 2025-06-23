package com.example.demo.dto;

import com.example.demo.entity.account.*;
import com.example.demo.entity.product.*;
import jakarta.persistence.*;
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
    private MultipartFile image;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;
    @NotNull
    private Integer category;
    private List<MultipartFile> images;

    public Product toEntity(Category category, Seller seller) {
      return new Product(0, name, info, "hello", price, 0, 0, 0, 0, stock, category, seller);
    }
  }

  @Data
  public static class Update {
    @NotNull
    private Integer pno;
    @NotEmpty
    private String info;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;
  }

  @Data
  public static class Summary {
    private Integer pno;
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
    private Integer pno;
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
