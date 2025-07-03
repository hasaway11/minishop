package com.example.demo.entity.account;

import com.example.demo.dto.*;
import lombok.*;

import java.time.*;
import java.time.temporal.*;

@Getter
@NoArgsConstructor
public class Member extends Account {
  private LocalDate birthDate;
  @Setter private String profile;
  private MemberLevel memberLevel;

  public Member(String username, String password, String email, String role, LocalDate birthDate, String profile, MemberLevel memberLevel) {
    super(username, password, email, LocalDate.now(), role);
    this.birthDate = birthDate;
    this.profile = profile;
    this.memberLevel = memberLevel;
  }

  public MemberDto.Read toRead() {
    long days = ChronoUnit.DAYS.between(getSignupDate(), LocalDate.now());
    return new MemberDto.Read(getUsername(), getEmail(), profile, getSignupDate(), birthDate, days, memberLevel.getLabel());
  }
}
