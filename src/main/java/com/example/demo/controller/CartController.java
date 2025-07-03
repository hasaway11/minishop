package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;
import java.util.*;

@RequiredArgsConstructor
@Validated
@RestController
public class CartController {
  private final CartService service;

  @GetMapping(value="/api/carts")
  public ResponseEntity<CartDto.Carts> read(Principal principal) {
    return ResponseEntity.ok(service.read(principal.getName()));
  }

  @PostMapping("/api/carts/{id}")
  public ResponseEntity<Integer> addToCart(@PathVariable int id, Principal principal) {
    return ResponseEntity.ok(service.addToCart(id, principal.getName()));
  }

  @PutMapping(value="/api/carts/{id}/inc")
  public ResponseEntity<CartDto.Carts> incQuantity(@PathVariable("id") int cartItemId, Principal principal) {
    return ResponseEntity.ok(service.increaseQuantity(cartItemId, principal.getName()));
  }

  @PutMapping(value="/api/carts/{id}/dec")
  public ResponseEntity<CartDto.Carts> decQuantity(@PathVariable("id") int cartItemId,  Principal principal) {
    return ResponseEntity.ok(service.decreaseQuantity(cartItemId, principal.getName()));
  }

  @DeleteMapping(value="/api/carts/{id}")
  public ResponseEntity<CartDto.Carts> removeCartItem(@PathVariable("id") int cartItemId,  Principal principal) {
    return ResponseEntity.ok(service.removeItem(cartItemId, principal.getName()));
  }

  // 복합값일때 @RequestParam 생략 불가능
  @DeleteMapping(value="/api/carts")
  public ResponseEntity<CartDto.Carts> removeCartItems(@RequestParam List<Integer> ids, Principal principal) {
    return ResponseEntity.ok(service.removeItems(ids, principal.getName()));
  }
}
