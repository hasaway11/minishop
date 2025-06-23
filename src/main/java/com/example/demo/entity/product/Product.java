package com.example.demo.entity.product;

import com.example.demo.dto.*;
import com.example.demo.entity.account.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
  @SequenceGenerator(name = "product_seq_gen", sequenceName = "product_seq", allocationSize = 1)
  private Integer pno;
  @Column(length=20)
  private String name;
  @Lob
  private String info;
  @Lob
  private String image;
  private Integer price;
  private Integer salesVolume;
  private Integer salesAmount;
  private Integer totalStars;
  private Integer reviewCount;
  private Integer stock;


  @ManyToOne
  @JoinColumn(name="category")
  private Category category;

  @ManyToOne
  @JoinColumn(name="seller")
  private Seller seller;

  public void changeFrom(ProductDto.Update dto) {
    this.info = dto.getInfo();
    this.price = dto.getPrice();
    this.stock = dto.getStock();
  }

  public ProductDto.Read toRead() {
    return new ProductDto.Read(pno, name, info, image, price, (double)totalStars/reviewCount, reviewCount, stock);
  }
}
