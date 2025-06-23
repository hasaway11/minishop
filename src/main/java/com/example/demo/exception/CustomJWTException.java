package com.example.demo.exception;

import lombok.*;

@AllArgsConstructor
@Getter
public class CustomJWTException extends RuntimeException {
	private String message;
}
