package com.example.demo.controller;

import com.example.demo.exception.*;
import com.example.demo.util.*;
import jakarta.servlet.http.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AuthController {
  @GetMapping("/api/check")
  public ResponseEntity<Void> authCheck(@RequestHeader("Authorization") String authHeader, HttpServletResponse response) {
    String accessToken = authHeader.substring(7);
    if(!checkExpiredToken(accessToken, response))
      return ResponseEntity.ok(null);
    return ResponseEntity.status(401).body(null);
  }

  @PostMapping("/api/refresh")
  public ResponseEntity<?> refresh(String refreshToken, HttpServletResponse response) {
    if (refreshToken == null)
      throw new CustomJWTException("NULL_REFRESH");
    // Refresh토큰 검증
    try {
      Map<String,Object> claims = JWTUtil.validateToken(refreshToken);
      String newAccessToken = JWTUtil.generateToken(claims, 10);
      return ResponseEntity.ok(newAccessToken);
    } catch(CustomJWTException e) {
      return ResponseEntity.status(401).body("LOGIN_REQUIRED");
    }
  }

  // 시간이 1시간 미만으로 남았다면
  private boolean checkTime(Long exp) {
    // JWT exp를 날짜로 변환
    Date expDate = new Date((long) exp * (1000));
    // 현재 시간과의 차이 계산 - 밀리세컨즈
    long gap = expDate.getTime() - System.currentTimeMillis();
    // 분단위 계산
    long leftMin = gap / (1000 * 60);
    // 1시간도 안남았는지..
    return leftMin < 60;
  }

  private boolean checkExpiredToken(String token, HttpServletResponse response) {
    try {
      JWTUtil.validateToken(token);
    } catch (CustomJWTException ex) {
      if (ex.getMessage().equals("TOKEN_EXPIRED"))
        return true;
    }
    return false;
  }
}
