package com.example.demo.entity.order;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@EqualsAndHashCode
public class OrderItemId {
  private Long orderId;
  private Long productId;
}
