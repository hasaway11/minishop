package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
public class CategoryController {
  private final CategoryService service;

  @GetMapping("/api/categories")
  public ResponseEntity<List<CategoryDto>> readAll() {
    return ResponseEntity.ok(service.readAll());
  }

  @GetMapping("/api/categories/minor")
  public ResponseEntity<List<CategoryDto>> readMinorCategory() {
    return ResponseEntity.ok(service.readMinorCategory());
  }
}
