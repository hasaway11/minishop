package com.example.demo.dto;

import lombok.*;

import java.util.*;

@Data
public class CategoryDto {
  private Integer categoryId;
  private String name;
  private List<CategoryDto> children;
}
