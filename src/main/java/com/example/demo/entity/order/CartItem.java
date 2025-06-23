package com.example.demo.entity.order;

import com.example.demo.dto.*;
import com.example.demo.entity.product.*;
import jakarta.persistence.*;

public class CartItem {
  private Integer cno;
  private String username;
  private Integer pno;
  private Integer quantity;
}
