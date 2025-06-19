package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDto {
  @Data
  public static class UsernameCheck {
    @NotEmpty
    @Pattern(regexp="^[a-z0-9]{6,10}$")
    private String username;
  }

  @Data
  public static class ResetPassword {
    @NotEmpty
    @Pattern(regexp="^[a-z0-9]{6,10}$")
    private String username;
  }

  @Data
  public static class PasswordChange {
    @NotEmpty
    @Pattern(regexp="^[a-zA-Z0-9]{6,10}$")
    private String currentPassword;
    @NotEmpty
    @Pattern(regexp="^[a-zA-Z0-9]{6,10}$")
    private String newPassword;
  }
}
