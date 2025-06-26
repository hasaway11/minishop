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
    Map<String,Object> map = new HashMap<>();
    map.put("username", username);
    map.put("password", password);
    map.put("roleName", roleName);
    return map;
  }
}
