package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDto {
  @Data
  public static class UsernameCheck {
    @NotEmpty(message="아이디는 필수입력입니다")
    @Pattern(regexp="^[a-z0-9]{6,10}$", message="아이디는 소문자와 숫자 6~10자입니다")
    private String username;
  }

  @Data
  public static class EmailCheck {
    @NotEmpty(message="이메일은 필수입력입니다")
    @Email
    private String email;
  }

  @Data
  public static class PasswordReset {
    @NotEmpty(message="아이디는 필수입력입니다")
    @Pattern(regexp="^[a-z0-9]{6,10}$", message="아이디는 소문자와 숫자 6~10자입니다")
    private String username;
    @NotEmpty(message="이메일은 필수입력입니다")
    @Email(message="잘못된 이메일입니다")
    private String email;
  }

  @Data
  public static class PasswordChange {
    @NotEmpty(message="현재 비밀번호는 필수입력입니다")
    @Pattern(regexp="^[a-zA-Z0-9]{6,10}$", message="비밀번호는 영숫자 6~10자입니다")
    private String currentPassword;
    @NotEmpty(message="새 비밀번호는 필수입력입니다")
    @Pattern(regexp="^[a-zA-Z0-9]{6,10}$", message="비밀번호는 영숫자 6~10자입니다")
    private String newPassword;
  }
}
