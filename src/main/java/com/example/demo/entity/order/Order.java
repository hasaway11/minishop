package com.example.demo.entity.order;

import com.example.demo.entity.account.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Getter
@Entity
public class Order {
  @Id @GeneratedValue
  private Long orderId;

  @ManyToOne
  private Member member; // 주문자

  private LocalDateTime orderDate;

  private String deliveryAddress;

  @Enumerated(EnumType.ORDINAL)
  private DeliveryStatus status;

  private Long totalPrice;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems = new ArrayList<>();
}
