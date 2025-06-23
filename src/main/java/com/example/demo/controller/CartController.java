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
public class CartController {
  private final CartService service;

  @GetMapping(value="/carts")
  public ResponseEntity<CartDto.Carts> read(Principal principal) {
    return ResponseEntity.ok(service.read(principal.getName()));
  }

  @PostMapping("/carts/{pno}")
  public ResponseEntity<CartDto.Carts> addToCart(@PathVariable int pno, Principal principal) {
    service.addToCart(pno, principal.getName());
    return ResponseEntity.ok(null);
  }
}
