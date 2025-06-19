package com.example.demo.entity.account;

import lombok.*;

@AllArgsConstructor
@Getter
public enum SellerLevel {
  POWER("파워 셀러"), PLATINUM("플래티넘 셀러"), PREMIUM("프리미엄 셀러");
  private final String label;
}
