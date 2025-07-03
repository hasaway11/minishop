package com.example.demo.advice;

import com.example.demo.entity.account.*;
import com.example.demo.exception.*;
import jakarta.validation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RestControllerAdvice
public class ProcessingFaultAdvice {
  // 500 : 처리 중 오류. 정말 다양한 이유로 발생

  // 검증 실패에 대한 예외처리
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> constraintViolationException(ConstraintViolationException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage().split(":")[1]);
  }

  // 사용자 정의 : 엔티티 클래스(회원,글,댓글)가 없을 때
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> entityNotFoundException(EntityNotFoundException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }

  // 사용자 정의 : 작업이 실패했을 때
  @ExceptionHandler(JobFailException.class)
  public ResponseEntity<String> jobFailException(JobFailException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }

  @ExceptionHandler(EmailVerificationRequireException.class)
  public ResponseEntity<String> emailVerificationRequireException() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 인증이 필요합니다");
  }

  @ExceptionHandler(OutOfStockException.class)
  public ResponseEntity<String> outOfStockException(OutOfStockException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }

  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  public ResponseEntity<String> sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }
}
