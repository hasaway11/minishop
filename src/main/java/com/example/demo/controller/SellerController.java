package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.account.*;
import com.example.demo.service.*;
import io.swagger.v3.oas.annotations.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.validation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;

@Validated
@RestController
@RequiredArgsConstructor
public class SellerController {
  private final SellerService service;

  @PreAuthorize("isAnonymous()")
  @Operation(summary= "셀러회원 가입", description="셀러회원 가입")
  @PostMapping("/api/seller/new")
  public ResponseEntity<Seller> signup(@Valid SellerDto.Signup dto, BindingResult br) {
    return ResponseEntity.ok(service.signup(dto));
  }

  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "판매자 정보 보기", description = "판매자 정보 보기")
  @GetMapping("/api/seller")
  public ResponseEntity<SellerDto.Read> read(Principal principal) {
    return ResponseEntity.ok(service.read(principal.getName()));
  }
}
