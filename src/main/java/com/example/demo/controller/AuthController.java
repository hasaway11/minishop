package com.example.demo.controller;

import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Controller
public class AuthController {
  @GetMapping(path = "/api/auth/check")
  public ResponseEntity<Map<String, String>> checkLogin(Authentication authentication, HttpSession session) {
    if (authentication != null && authentication.isAuthenticated()) {
      String username = authentication.getName();
      String role = authentication.getAuthorities().stream().map(a->a.getAuthority()).findFirst().orElse("");
      return ResponseEntity.ok(Map.of("username", username,"role", role));
    }
    return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
  }
}
