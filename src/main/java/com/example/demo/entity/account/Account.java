package com.example.demo.entity.account;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 또는 SINGLE_TABLE
@DiscriminatorColumn(name = "account_type")
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public abstract class Account {
  @Id
  @Column(length=10)
  private String username;
  @Column(length=60)
  private String password;
  @Column(length=30)
  private String email;
  private LocalDate signupDate;

  public Account(String username) {
    this.username = username;
  }

  public void changePassword(String encodedPassword) {
    this.password = encodedPassword;
  }
}
