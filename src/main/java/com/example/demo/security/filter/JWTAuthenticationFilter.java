package com.example.demo.security.filter;

import java.io.*;
import java.util.*;

import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.web.filter.*;

import com.example.demo.dto.*;
import com.example.demo.util.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
	List<String> whitelist = Arrays.asList("/api/member", "/api/products/view");
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		if(request.getMethod().equals("OPTIONS"))
			return true;
		String path = request.getRequestURI();
		return whitelist.stream().anyMatch(path::startsWith);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		String authHeader = request.getHeader("Authorization");

		// 토큰이 없거나 Bearer로 시작하지 않으면 anonymous로 둔 채 다음 필터로
		if(authHeader==null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String accessToken = request.getHeader("Authorization").substring(7);
		Map<String, Object> claims = JWTUtil.validateToken(accessToken);
		String username = (String)claims.get("username");
		String roleName = (String)claims.get("roleName");
		CustomUserDetails dto = new CustomUserDetails(username, null, roleName);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto, null, dto.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);
		filterChain.doFilter(request, response);
	}
}
