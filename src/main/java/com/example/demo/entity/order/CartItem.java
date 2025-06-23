package com.example.demo.entity.order;

import com.example.demo.dto.*;
import com.example.demo.entity.product.*;
import jakarta.persistence.*;

@Entity
public class CartItem {
  @EmbeddedId
  private CartItemId id;

  @ManyToOne
  @MapsId("pno") // 복합키의 필드명과 일치시킴
  @JoinColumn(name="pno")
  private Product product;

  private int quantity;
}
