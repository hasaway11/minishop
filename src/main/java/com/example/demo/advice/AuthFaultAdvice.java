package com.example.demo.advice;

import com.example.demo.exception.*;
import io.jsonwebtoken.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestControllerAdvice
public class AuthFaultAdvice {
  @ExceptionHandler(CustomJWTException.class)
  public ResponseEntity<String> customJWTExceptionHandler(CustomJWTException e) {
    String message = e.getMessage();
    if(message.equals("TOKEN_EXPIRED")) {
      return ResponseEntity.status(401).body("TOKEN_EXPIRED");
    }
    return ResponseEntity.status(401).body("INVALID_TOKEN");
  }
}
