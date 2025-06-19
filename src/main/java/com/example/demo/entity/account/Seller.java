package com.example.demo.entity.account;

import jakarta.persistence.*;
import org.hibernate.annotations.*;

@Entity
@DiscriminatorValue("SELLER")
public class Seller extends Account {
  @Comment("회사 이름")
  @Column(length=30)
  private String companyName;
  @Comment("대표자 이름")
  @Column(length=10)
  private String representative;
  @Comment("소재지")
  @Column(length=50)
  private String address;
  @Enumerated(value=EnumType.ORDINAL)
  private SellerLevel sellerLevel = SellerLevel.POWER;
}
