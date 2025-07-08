package com.example.demo.dto;

import com.example.demo.entity.product.*;
import com.fasterxml.jackson.annotation.*;
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
    private Integer rating;
    @NotNull
    private Integer orderItemId;
    @NotNull
    private Integer productId;

    public Review toReview(String loginId) {
      return null;
    }

    public Review toEntity(String loginId) {
      return new Review(null, loginId, content, rating, LocalDateTime.now(), productId);
    }
  }

  @Data
  public static class Read {
    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime writeTime;
    private int rating;
    private String content;
    private String name;
    private String image;
  }
}

