package com.example.demo.entity.account;

import com.example.demo.dto.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.time.temporal.*;

@Entity
@DiscriminatorValue("MEMBER")
public class Member extends Account {
  private LocalDate birthDate;
  @Lob
  private String profile;
  @Enumerated(value=EnumType.ORDINAL)
  private MemberLevel memberLevel;

  public Member(String username, String password, String email, LocalDate birthday, String profile) {
    super(username, password, email, LocalDate.now());
    this.birthDate = birthday;
    this.profile = profile;
    this.memberLevel = MemberLevel.NORMAL;
  }

  public MemberDto.Read toRead() {
    long days = ChronoUnit.DAYS.between(getSignupDate(), LocalDate.now());
    return new MemberDto.Read(getUsername(), getEmail(), profile, getSignupDate(), birthDate, days, memberLevel);
  }
}
