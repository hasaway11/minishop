package com.example.demo.entity.order;

import jakarta.persistence.*;
import lombok.*;

import java.io.*;

@Embeddable
@EqualsAndHashCode
public class CartItemId implements Serializable {
  private String username;
  private Integer pno;
}
