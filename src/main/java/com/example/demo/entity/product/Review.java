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
  private Integer rating;
  private LocalDateTime writeTime;
  private Integer productId;
}
