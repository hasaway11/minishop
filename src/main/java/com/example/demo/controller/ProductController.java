package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;

@RequiredArgsConstructor
@Validated
@RestController
public class ProductController {
  private final ProductService service;

  @GetMapping("/api/products")
  public ResponseEntity<PageResponse<ProductDto.Summary>> findSummaries(@RequestParam(defaultValue="1") int pageno) {
    PageResponse<ProductDto.Summary> products = service.findSummaries(pageno);
    return ResponseEntity.ok(products);
  }

  @GetMapping("/api/seller/products/{pno}")
  public ResponseEntity<ProductDto.Read> read(@PathVariable int pno, Principal principal) {
    return ResponseEntity.ok(service.read(pno));
  }
}
