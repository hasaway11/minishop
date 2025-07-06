package com.example.demo.entity.order;

import lombok.*;

@Getter
@AllArgsConstructor
public enum OrderStatus {
  CREATE("주문작성"), EXPIRED("만료"), PAY("결제완료"), SHIPPING("배송중"), CANCEL("취소"), RETURN("환불"), COMPLETE("배송 완료");

  private final String label;
}
