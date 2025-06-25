package com.example.demo.entity.order;

import lombok.*;

@AllArgsConstructor
@Getter
public enum OrderStatus {
  CREATE("주문작성"), EXPIRED("만료"), PAY("결제완료"), SHIPPING("배송중"), CANCEL("취소"), RETURN("환불");

  private final String label;
}
