package com.example.demo.dto;

import com.example.demo.entity.account.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.*;
import org.springframework.security.crypto.password.*;

import java.time.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SellerDto {
  @Data
  public static class Signup {
    @NotEmpty
    @Pattern(regexp="^[a-z0-9]{6,10}$")
    private String username;
    @NotEmpty
    @Pattern(regexp="^[a-zA-Z0-9]{6,10}$")
    private String password;
    @NotEmpty
    @Email
    private String email;
    @NotNull
    private LocalDate signupDate;
    @NotEmpty
    private String companyName;
    @NotEmpty
    private String representative;
    @NotEmpty
    private String address;
    private SellerLevel sellerLevel;

    public Seller toEntity(PasswordEncoder passwordEncoder) {
      String encodedPassword = passwordEncoder.encode(password);
      return new Seller(username, encodedPassword, email, companyName, representative, address);
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
    private SellerLevel level;
    private Integer salesCount;
    private Integer salesAmount;
  }
}
