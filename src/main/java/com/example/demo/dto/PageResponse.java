package com.example.demo.dto;

import lombok.*;

import java.util.*;

@Data
@AllArgsConstructor
public class PageResponse<T> {
  private List<T> contents;
  private int pageno;
  private int pagesize;
  private int totalCount;
}
