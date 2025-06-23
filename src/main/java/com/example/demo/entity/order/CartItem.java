package com.example.demo.entity.order;

import com.example.demo.dto.*;
import com.example.demo.entity.product.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
public class CartItem {
  private Integer cartItemId;
  private String username;
  private Integer productId;
  private Integer quantity;
}
