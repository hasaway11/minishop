package com.example.demo.entity.product;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
public class Image {
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="image_seq_gen")
  @SequenceGenerator(name="image_seq_gen", sequenceName="image_seq", allocationSize=1)
  private int ino;
  private String name;

  @ManyToOne
  @JoinColumn(name="pno")
  private Product product;
}
