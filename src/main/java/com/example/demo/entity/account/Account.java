package com.example.demo.entity.account;

import lombok.*;

import java.time.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
  private String username;
  private String password;
  private String email;
  private LocalDate signupDate;
  private String role;

  public Account(String username) {
    this.username = username;
  }
}
