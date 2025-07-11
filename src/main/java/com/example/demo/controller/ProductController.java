package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
public class ProductController {
  private final ProductService service;

  @GetMapping("/api/products")
  public ResponseEntity<PageResponse<ProductDto.Summary>> findSummaries(@RequestParam(defaultValue="1") int pageno, @RequestParam String seller) {
    return ResponseEntity.ok(service.findSummaries(pageno, seller));
  }

  @GetMapping("/api/products/{id}")
  public ResponseEntity<ProductDto.Read> read(@PathVariable int id) {
    System.out.println(service.read(id));
    return ResponseEntity.ok(service.read(id));
  }
}
