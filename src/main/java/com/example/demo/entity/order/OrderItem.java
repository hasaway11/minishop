package com.example.demo.entity.order;

import com.example.demo.entity.product.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
public class OrderItem {

  @EmbeddedId
  private OrderItemId id;

  @ManyToOne
  @MapsId("orderId")
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne
  @MapsId("productId")
  @JoinColumn(name = "product_id")
  private Product product;

  private int quantity;

  private int price;
}
