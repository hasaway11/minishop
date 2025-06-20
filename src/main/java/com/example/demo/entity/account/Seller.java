package com.example.demo.entity.account;

import com.example.demo.dto.*;
import jakarta.persistence.*;
import org.hibernate.annotations.*;

import java.time.*;
import java.time.temporal.*;

@Entity
@DiscriminatorValue("SELLER")
public class Seller extends Account {
  private static final int MIN_COUNT_FOR_PLATINUM = 30;
  private static final int MIN_COUNT_FOR_PREMIUM = 50;

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
  private Integer salesCount;
  private Integer salesAmount;

  public Seller(String username, String password, String email, String companyName, String representative, String address) {
    super(username, password, email, LocalDate.now());
    this.companyName = companyName;
    this.representative = representative;
    this.address = address;
    this.sellerLevel = SellerLevel.POWER;
    this.salesCount = 0;
    this.salesAmount = 0;
  }

  public SellerDto.Read toRead() {
    long days = ChronoUnit.DAYS.between(getSignupDate(), LocalDate.now());
    return new SellerDto.Read(getUsername(), getEmail(), companyName, getSignupDate(), days, representative, address, sellerLevel, salesCount, salesAmount );
  }

  public void addSales(int count, int price) {
    this.salesCount+=count;
    this.salesAmount+=price;
    if(this.sellerLevel==SellerLevel.POWER && salesCount>=MIN_COUNT_FOR_PLATINUM)
      this.sellerLevel = SellerLevel.PLATINUM;
    if(this.sellerLevel==SellerLevel.PLATINUM && salesCount>=MIN_COUNT_FOR_PREMIUM)
      this.sellerLevel = SellerLevel.PREMIUM;
  }
}
