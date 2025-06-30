package com.example.demo.dto;

import com.example.demo.entity.account.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.crypto.password.*;

import java.time.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SellerDto {
  @Data
  public static class EmailVerification {
    @NotEmpty(message="이메일은 필수입력입니다")
    @Email
    private String email;
  }

  @Data
  public static class CheckVerifyCode {
    @NotEmpty(message="확인코드는 필수입력입니다")
    @Pattern(regexp="^[a-zA-Z0-9]{10}$", message="확인코드는 영숫자 10자입니다")
    private String code;
  }

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
    private LocalDate signupDate;
    @NotEmpty(message="회사명은 필수입력입니다")
    private String companyName;
    @NotEmpty(message="대표자 이름은 필수입력입니다")
    private String representative;
    @NotEmpty(message="회사 주소는 필수입력입니다")
    private String address;
    private SellerLevel sellerLevel;

    public Seller toEntity(PasswordEncoder passwordEncoder) {
      String encodedPassword = passwordEncoder.encode(password);
      return new Seller(username, encodedPassword, email, LocalDate.now(), "SELLER", companyName, representative, address, SellerLevel.POWER, 0, 0);
    }
  }

  @Data
  @AllArgsConstructor
  public static class Read {
    private String username;
    private String email;
    private String companyName;
    @JsonFormat(pattern="yyyy년 MM월 dd일")
    private LocalDate signupDate;
    private long days;
    private String representative;
    private String address;
    private String level;
    private Integer salesCount;
    private Integer salesAmount;
  }
}
