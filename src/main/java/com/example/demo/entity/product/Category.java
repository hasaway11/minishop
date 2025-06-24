package com.example.demo.entity.product;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@NoArgsConstructor
public class Category {
  private Integer categoryId;
  private String name;
  private Integer parent;
}
