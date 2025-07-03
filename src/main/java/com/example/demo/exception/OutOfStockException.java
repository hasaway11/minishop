package com.example.demo.exception;

import lombok.*;

@AllArgsConstructor
@Getter
public class OutOfStockException extends RuntimeException {
  private String message;
}
