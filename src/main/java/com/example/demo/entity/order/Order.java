package com.example.demo.entity.order;

import com.example.demo.entity.account.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
  private Integer orderId;
  private String username;
  private LocalDateTime orderDate;
  private String deliveryAddress;
  private DeliveryStatus status;
  private Integer orderTotalPrice;
}
