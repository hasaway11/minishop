package com.example.demo.controller;

import java.util.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.*;
import com.example.demo.util.*;

@RestController
public class APIRefreshController {
	@GetMapping("/api/member/refresh")
	public ResponseEntity<Map<String, Object>> refresh(@RequestHeader("Authorization") String authHeader, String refreshToken) {
		if (refreshToken == null) 
			throw new CustomJWTException("NULL_REFRASH");
		if (authHeader == null || authHeader.length() < 7) 
			throw new CustomJWTException("INVALID_STRING");

		String accessToken = authHeader.substring(7);
		
		// Access 토큰이 만료되지 않았다면
		if (!checkExpiredToken(accessToken))
			return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));

		// Refresh토큰 검증
		Map<String,Object> claims = JWTUtil.validateToken(refreshToken);
		String newAccessToken = JWTUtil.generateToken(claims, 10);
		String newRefreshToken = checkTime((Integer) claims.get("exp")) ? JWTUtil.generateToken(claims, 60 * 24) : refreshToken;
		return ResponseEntity.ok(Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken));
	}

	// 시간이 1시간 미만으로 남았다면
	private boolean checkTime(Integer exp) {
		// JWT exp를 날짜로 변환
		Date expDate = new Date((long) exp * (1000));
		// 현재 시간과의 차이 계산 - 밀리세컨즈
		long gap = expDate.getTime() - System.currentTimeMillis();
		// 분단위 계산
		long leftMin = gap / (1000 * 60);
		// 1시간도 안남았는지..
		return leftMin < 60;
	}

	private boolean checkExpiredToken(String token) {
		try {
			JWTUtil.validateToken(token);
		} catch (CustomJWTException ex) {
			if (ex.getMessage().equals("Expired"))
				return true;
		}
		return false;
	}
}