package com.example.demo.dto;

import com.example.demo.entity.account.*;
import com.example.demo.util.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.crypto.password.*;
import org.springframework.web.multipart.*;

import java.time.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDto {
  @Data
  public static class Signup {
    @NotEmpty
    @Pattern(regexp="^[a-z0-9]{6,10}$")
    private String username;
    @NotEmpty
    @Pattern(regexp="^[a-zA-Z0-9]{6,10}$")
    private String password;
    @Email
    @NotEmpty
    private String email;
    @NotNull
    private LocalDate birthday;
    private MultipartFile profile;

    public Member toEntity(PasswordEncoder passwordEncoder) {
      String profileString = FunctionUtil.getProfile(profile, false).get();
      String encodedPassword = passwordEncoder.encode(password);
      return new Member(username, encodedPassword, email, birthday, profileString);
    }
  }

  @Data
  @AllArgsConstructor
  public static class Read {
    private String username;
    private String email;
    private String profile;
    @JsonFormat(pattern="yyyy년 MM월 dd일")
    private LocalDate signupDate;
    private LocalDate birthDate;
    // 가입기간
    private long days;
    private MemberLevel level;
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

  @Data
  public static class changeProfile {
    @NotNull
    private MultipartFile profile;
  }
}
