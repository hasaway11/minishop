package com.example.demo.entity.account;

import jakarta.persistence.*;

import java.time.*;

@Entity
@DiscriminatorValue("MEMBER")
public class Member extends Account {
  private LocalDate birthday;
  @Enumerated(value=EnumType.ORDINAL)
  private MemberLevel memberLevel = MemberLevel.NORMAL;
}
