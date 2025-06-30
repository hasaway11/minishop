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
  @Operation(summary= "확인코드 신청", description="셀러회원으로 가입하기 위해 이메일로 확인코드를 신청")
  @PostMapping("/api/email-verification/request")
  public ResponseEntity<String> requestEmailVerification(@Valid SellerDto.EmailVerification dto, BindingResult br) {
    // 새로운 이메일 -> 이메일 신청 코드 발급 후 확인하라는 메시지
    // 이메일이 존재하면 -> 확인하라는 메시지
    service.requestEmailVerification(dto);
    return ResponseEntity.ok("이메일로 가입 확인코드를 보냈습니다");
  }

  @PreAuthorize("isAnonymous()")
  @Operation(summary= "확인코드 검증", description="확인코드 검증")
  @PostMapping("/api/email-verification/check")
  public ResponseEntity<String> checkVerifyCode(@Valid SellerDto.CheckVerifyCode dto, BindingResult br) {
    service.checkVerifyCode(dto);
    return ResponseEntity.ok("이메일로 가입 확인코드를 보냈습니다");
  }

  @PreAuthorize("isAnonymous()")
  @Operation(summary= "셀러회원 가입", description="셀러회원 가입")
  @PostMapping("/api/sellers/new")
  public ResponseEntity<Seller> signup(@Valid SellerDto.Signup dto, BindingResult br) {
    return ResponseEntity.ok(service.signup(dto));
  }

  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "판매자 정보 보기", description = "판매자 정보 보기")
  @GetMapping("/api/sellers")
  public ResponseEntity<SellerDto.Read> read(Principal principal) {
    return ResponseEntity.ok(service.read(principal.getName()));
  }
}
