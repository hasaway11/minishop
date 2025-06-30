package com.example.demo.security;

import com.example.demo.dto.*;
import com.example.demo.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    CustomUserDetails user = (CustomUserDetails)authentication.getPrincipal();
    Map<String,Object> claims = user.getClaims();
    claims.put("accessToken", JWTUtil.generateToken(claims, 10));
    claims.put("refreshToken", JWTUtil.generateToken(claims, 1440));
    ResponseUtil.sendJsonResponse(response, 200, claims);
  }
}








