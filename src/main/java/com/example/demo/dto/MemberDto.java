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
    @NotEmpty(message="아이디는 필수입력입니다")
    @Pattern(regexp="^[a-z0-9]{6,10}$", message="아이디는 소문자와 숫자 6~10자입니다")
    private String username;
    @NotEmpty(message="비밀번호는 필수입력입니다")
    @Pattern(regexp="^[a-zA-Z0-9]{6,10}$", message="비밀번호는 영숫자 6~10자입니다")
    private String password;
    @NotEmpty(message="이메일은 필수입력입니다")
    @Email(message="잘못된 이메일입니다")
    private String email;
    @NotNull(message="생일은 필수입력입니다")
    private LocalDate birthday;
    private MultipartFile profile;
    @NotEmpty(message="확인코드는 필수입력입니다")
    @Pattern(regexp="^[a-zA-Z0-9]{10}$", message="확인코드는 영숫자 10자입니다")
    private String code;

    public Member toEntity(PasswordEncoder passwordEncoder) {
      String encodedPassword = passwordEncoder.encode(password);
      return new Member(username, encodedPassword, email, birthday, null);
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
    private String level;
  }

  @Data
  public static class changeProfile {
    @NotNull(message="프로필 사진은 필수입력입니다")
    private MultipartFile profile;
  }
}
