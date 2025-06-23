package com.example.demo.util;

import java.nio.charset.*;
import java.time.*;
import java.util.*;

import javax.crypto.*;

import com.example.demo.exception.*;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JWTUtil {
	private static final String KEY = "1234567890123456789012345678901234567890";
	private static final SecretKey SECRET_KEY;

	static {
		SECRET_KEY = Keys.hmacShaKeyFor(KEY.getBytes(StandardCharsets.UTF_8));
	}

	public static String generateToken(Map<String, Object> valueMap, int min) {
		return Jwts.builder().header().add(Map.of("typ", "JWT")).and().claims().add(valueMap).and()
				.issuedAt(Date.from(ZonedDateTime.now().toInstant()))
				.expiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant())).signWith(SECRET_KEY).compact();
	}

	public static Map<String,Object> validateToken(String token) {
		try {
			return Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
		} catch (MalformedJwtException e) {
			throw new CustomJWTException("MalFormed");
		} catch (ExpiredJwtException e) {
			throw new CustomJWTException("Expired");
		} catch (InvalidClaimException e) {
			throw new CustomJWTException("Invalid");
		} catch (JwtException e) {
			throw new CustomJWTException("JWTError");
		} catch (Exception e) {
			throw new CustomJWTException("Error");
		}
	}
}
