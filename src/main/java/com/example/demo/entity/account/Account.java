package com.example.demo.entity.account;

import lombok.*;

import java.time.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public abstract class Account {
  private String username;
  private String password;
  private String email;
  private LocalDate signupDate;

  public Account(String username) {
    this.username = username;
  }

  public void changePassword(String encodedPassword) {
    this.password = encodedPassword;
  }
}
