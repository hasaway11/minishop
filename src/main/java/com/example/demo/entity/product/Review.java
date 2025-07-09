package com.example.demo.entity.product;

import com.fasterxml.jackson.annotation.*;
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
  @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
  private LocalDateTime writeTime;
  private Integer productId;
}
