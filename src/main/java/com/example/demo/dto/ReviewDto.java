package com.example.demo.dto;

import com.example.demo.entity.product.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.*;
import java.util.*;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class ReviewDto {
  @Data
  public static class Create {
    @NotEmpty
    private String content;
    @NotNull
    private Integer star;
    @NotNull
    private Integer productId;

    public Review toReview(String loginId) {
      return null;
    }
  }

  @Getter
  @AllArgsConstructor
  public static class ReviewList {
    private List<Review> reviews;
    private Integer countOfReview;
    private Double rating;
  }
}

