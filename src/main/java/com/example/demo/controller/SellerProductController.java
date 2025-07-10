package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.product.*;
import com.example.demo.service.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.access.annotation.*;
import org.springframework.validation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;

@Secured(("ROLE_SELLER"))
@RequiredArgsConstructor
@Validated
@RestController
public class SellerProductController {
  private final SellerProductService service;

  @PostMapping("/api/seller/products/new")
  public ResponseEntity<?> create(@ModelAttribute @Valid ProductDto.Create dto, BindingResult br, Principal principal) {
    Product product = service.create(dto, principal.getName());
    return ResponseEntity.ok(product);
  }

  @GetMapping("/api/seller/products")
  public ResponseEntity<PageResponse<ProductDto.Summary>> findAllBySeller(@RequestParam(defaultValue="1") Integer pageno, Principal principal) {
    PageResponse<ProductDto.Summary> products = service.findBySeller(pageno, principal.getName());
    return ResponseEntity.ok(products);
  }

  @GetMapping("/api/seller/products/{productId}")
  public ResponseEntity<ProductDto.Read> read(@PathVariable int productId, Principal principal) {
    return ResponseEntity.ok(service.read(productId, principal.getName()));
  }

  @PutMapping("/api/seller/products/{productId}")
  public ResponseEntity<Void> update(@ModelAttribute @Valid ProductDto.Update dto, BindingResult br, Principal principal) {
    service.update(dto, principal.getName());
    return ResponseEntity.ok(null);
  }

  @DeleteMapping("/api/seller/products/{productId}")
  public ResponseEntity<Void> delete(@PathVariable int productId, Principal principal) {
    service.delete(productId, principal.getName());
    return ResponseEntity.ok(null);
  }
}
