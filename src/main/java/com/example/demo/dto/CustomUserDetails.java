package com.example.demo.dto;

import lombok.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;

@Getter
public class CustomUserDetails extends User {
  private String username;
  private String password;
  private String roleName;

  public CustomUserDetails(String username, String password, String roleName) {
    super(username, password, List.of(new SimpleGrantedAuthority("ROLE_" + roleName)));
    this.username = username;
    this.password = password;
    this.roleName = roleName;
  }

  public Map<String, Object> getClaims() {
    return Map.of("username", username, "password", password, "roleName", roleName);
  }
}
