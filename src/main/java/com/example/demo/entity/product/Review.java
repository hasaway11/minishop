package com.example.demo.entity.product;

import lombok.*;

import java.time.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
  private Integer id;
  private String writer;
  private String content;
  private Integer star;
  private LocalDate writeDate;
  private Integer productId;
}
