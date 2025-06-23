package com.example.demo.security;

import com.example.demo.dao.jpa.*;
import com.example.demo.dto.*;
import com.example.demo.entity.account.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Component
public class CustomUserDetailsService implements UserDetailsService  {
  @Autowired
  private AccountRepository accountDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountDao.findById(username).orElseThrow(()->new UsernameNotFoundException("사용자가 없습니다"));
    String role = "MEMBER";
    if(account instanceof Seller)
      role = "SELLER";
    return new CustomUserDetails(account.getUsername(), account.getPassword(), role);
  }
}
