package com.example.demo.entity.product;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor
public class Category {
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="category_seq_gen")
  @SequenceGenerator(name="category_seq_gen", sequenceName="category_seq", allocationSize=1)
  private int cno;
  private String name;

  @ManyToOne
  @JoinColumn(name="parent_id")
  private Category parent;

  // 하위 카테고리들
  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Category> children = new ArrayList<>();

  // 이 카테고리에 속한 상품들
  @OneToMany(mappedBy = "category")
  private List<Product> products = new ArrayList<>();

  public Category(String name) {
    this.name = name;
  }
}
