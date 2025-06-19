package com.example.demo.entity.order;

import com.example.demo.entity.product.*;
import jakarta.persistence.*;

@Entity
public class CartItem {
  @EmbeddedId
  private CartItemId id;

  @ManyToOne
  @MapsId("productId") // 복합키의 필드명과 일치시킴
  @JoinColumn(name="product_id")
  private Product product;

  private int quantity;
}
