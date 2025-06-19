package com.example.demo.entity.product;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
public class Category {
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="category_seq_gen")
  @SequenceGenerator(name="category_seq_gen", sequenceName="category_seq", allocationSize=1)
  private int cno;
  private String name;

  @ManyToOne
  @JoinColumn(name="parent_id")
  private Category parent;

  @OneToMany(mappedBy="category")
  private List<Product> products = new ArrayList<>();
}
