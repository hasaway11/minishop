package com.example.demo.entity.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_gen")
  @SequenceGenerator(name = "product_seq_gen", sequenceName = "product_seq", allocationSize = 1)
  private int pno;
  @Column(length=20)
  private String name;
  @Lob
  private String info;
  private int price;
  private int salesVolume;
  private int totalStars;
  private int reviewCount;
  private int stock;

  @ManyToOne
  @JoinColumn(name="category_id")
  private Category category;
}
