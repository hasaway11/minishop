package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.*;

import io.swagger.v3.oas.annotations.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import lombok.*;

import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
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

  @PreAuthorize("isAnonymous()")
  @Operation(summary= "아이디 확인", description="아이디가 사용가능한 지 확인")
  @GetMapping("/api/accounts/check-username")
  public ResponseEntity<String> checkUsernameAvailability(@ModelAttribute @Valid AccountDto.UsernameCheck dto, BindingResult br) {
    boolean result = service.checkUsernameAvailability(dto);
    if (result)
      return ResponseEntity.ok("사용가능한 아이디입니다");
    return ResponseEntity.status(HttpStatus.CONFLICT).body("사용중인 아이디입니다");
  }

  @PreAuthorize("isAnonymous()")
  @Operation(summary="아이디 찾기", description="가입한 이메일로 아이디를 찾는다")
  @GetMapping("/api/accounts/find-username")
  public ResponseEntity<String> findUsernameByEmail(@NotEmpty @Email String email) {
    Optional<String> result = service.findUseranameByEmail(email);
    if(result.isPresent())
      return ResponseEntity.ok(result.get());
    return ResponseEntity.status(HttpStatus.CONFLICT).body("사용자를 찾을 수 없습니다");
  }

  @PreAuthorize("isAnonymous()")
  @Operation(summary="임시비밀번호 발급", description="아이디로 임시비밀번호를 발급")
  @PostMapping("/api/accounts/reset-password")
  public ResponseEntity<String> issueTemporaryPassword(@ModelAttribute @Valid AccountDto.PasswordReset dto, BindingResult br) {
    boolean result = service.issueTemporaryPassword(dto);
    if(result)
      return ResponseEntity.ok("임시비밀번호를 이메일로 보냈습니다");
    return ResponseEntity.status(HttpStatus.CONFLICT).body("사용자를 찾을 수 없습니다");
  }

  @PreAuthorize("isAuthenticated()")
  @Operation(summary="비밀번호 확인", description="현재 접속 중인 사용자의 비밀번호를 재확인")
  @GetMapping("/api/accounts/check-password")
  public ResponseEntity<String> verifyPassword(@RequestParam @NotEmpty String password, Principal principal) {
    boolean result = service.verifyPassword(password, principal.getName());
    if(result)
      return ResponseEntity.ok("비밀번호 확인 성공");
    return ResponseEntity.status(HttpStatus.CONFLICT).body("비밀번호 확인 실패");
  }

  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "비밀번호 변경", description = "기존 비밀번호, 새 비밀번호로 비밀번호 변경")
  @PutMapping("/api/accounts/password")
  public ResponseEntity<String> updatePassword(@ModelAttribute @Valid AccountDto.PasswordChange dto, BindingResult br, Principal principal) {
    boolean result = service.updatePassword(dto, principal.getName());
    return result? ResponseEntity.ok(null):ResponseEntity.status(409).body(null);
  }
}
