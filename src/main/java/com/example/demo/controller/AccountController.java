package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.*;

import io.swagger.v3.oas.annotations.*;
import jakarta.servlet.http.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.*;
import org.springframework.validation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;
import java.util.*;

@Validated
@RestController
@RequiredArgsConstructor
public class AccountController {
  private final AccountService service;

  @GetMapping(path = "/api/account/auth")
  public ResponseEntity<Map<String, String>> checkLogin(Authentication authentication, HttpSession session) {
    if (authentication != null && authentication.isAuthenticated()) {
      String username = authentication.getName();
      String role = authentication.getAuthorities().stream().map(a->a.getAuthority()).findFirst().orElse("");
      return ResponseEntity.ok(Map.of("username", username,"role", role));
    }
    return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
  }

  @PreAuthorize("isAnonymous()")
  @Operation(summary= "아이디 확인", description="아이디가 사용가능한 지 확인")
  @GetMapping("/api/account/check-username")
  public ResponseEntity<String> idAvailable(@Valid AccountDto.UsernameCheck dto, BindingResult br) {
    boolean result = service.idAvailable(dto);
    if (result) return ResponseEntity.ok("사용가능한 아이디입니다");
    return ResponseEntity.status(HttpStatus.CONFLICT).body("사용중인 아이디입니다");
  }

  @PreAuthorize("isAnonymous()")
  @Operation(summary= "체크코드 보내기", description="이메일을 전달받아 체크코드 발송")
  @GetMapping("/api/account/send-verification-code")
  public ResponseEntity<String> sendVerificationCode(@NotEmpty @Email String email, BindingResult br, HttpSession session) {
    String checkCode = service.sendVerificationCode(email);
    session.setAttribute("checkCode", checkCode);
    return ResponseEntity.ok("이메일을 확인하세요");
  }

  @PreAuthorize("isAnonymous()")
  @Operation(summary="아이디 찾기", description="가입한 이메일로 아이디를 찾는다")
  @GetMapping("/api/account/username")
  public ResponseEntity<String> findUsername(@NotEmpty @Email String email) {
    Optional<String> result = service.findUseraname(email);
    if(result.isPresent()) return ResponseEntity.ok(result.get());
    return ResponseEntity.status(HttpStatus.CONFLICT).body("사용자를 찾을 수 없습니다");
  }

  @PreAuthorize("isAnonymous()")
  @Operation(summary="임시비밀번호 발급", description="아이디로 임시비밀번호를 발급")
  @PutMapping("/api/account/password")
  public ResponseEntity<String> resetPassword(@ModelAttribute @Valid AccountDto.ResetPassword dto, BindingResult br) {
    boolean result = service.resetPassword(dto);
    if(result) return ResponseEntity.ok("임시비밀번호를 이메일로 보냈습니다");
    return ResponseEntity.status(HttpStatus.CONFLICT).body("사용자를 찾을 수 없습니다");
  }

  @PreAuthorize("isAuthenticated")
  @Operation(summary="비밀번호 확인", description="현재 접속 중인 사용자의 비밀번호를 재확인")
  @GetMapping("/api/account/check-password")
  public ResponseEntity<String> checkPassword(@RequestParam String password, Principal principal) {
    boolean result = service.checkPassword(password, principal.getName());
    if(result) return ResponseEntity.ok("비밀번호 확인 성공");
    return ResponseEntity.status(HttpStatus.CONFLICT).body("비밀번호 확인 실패");
  }
}
