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
import org.springframework.web.multipart.*;

import java.security.*;

@Validated
@RestController
@RequiredArgsConstructor
public class MemberController {
  private final MemberService service;

  @PreAuthorize("isAnonymous()")
  @Operation(summary= "일반회원 가입", description="일반회원 가입 및 프로필 사진 업로드")
  @PostMapping("/api/member/new")
  public ResponseEntity<Member> signup(MemberDto.Signup dto, BindingResult br) {
    return ResponseEntity.ok(service.signup(dto));
  }

  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "내 정보 보기", description = "내 정보 보기")
  @GetMapping("/api/member")
  public ResponseEntity<MemberDto.Read> read(Principal principal) {
    MemberDto.Read dto = service.read(principal.getName());
    return ResponseEntity.ok(dto);
  }

  // 비번 변경
  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "비밀번호 변경", description = "기존 비밀번호, 새 비밀번호로 비밀번호 변경")
  @PutMapping("/api/member/password")
  public ResponseEntity<String> changePassword(@ModelAttribute @Valid MemberDto.PasswordChange dto, BindingResult br, Principal principal) {
    boolean result = service.changePassword(dto, principal.getName());
    return result? ResponseEntity.ok(null):ResponseEntity.status(409).body(null);
  }

  // 프사 변경
  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "프사 변경", description = "프사를 변경")
  @PutMapping("/api/member/profile")
  public ResponseEntity<Void> changeProfile(@Valid MemberDto.changeProfile dto, BindingResult br, Principal principal) {
    boolean result = service.changeProfile(dto, principal.getName());
    return result? ResponseEntity.ok(null):ResponseEntity.status(409).body(null);
  }
}
