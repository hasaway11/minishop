package com.example.demo.entity.account;

import lombok.*;

import java.time.*;

@Getter
public class EmailVerification {
  private String email;
  private String code;
  private LocalDateTime sendAt = LocalDateTime.now();
  private LocalDateTime expiresAt = LocalDateTime.now().plusDays(1);
  private boolean isVerify = false;

  public EmailVerification(String email, String code) {
    this.email = email;
    this.code = code;
  }
}
