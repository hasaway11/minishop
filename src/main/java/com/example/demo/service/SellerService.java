package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dao.jpa.*;
import com.example.demo.dto.*;
import com.example.demo.entity.account.*;
import com.example.demo.exception.*;
import lombok.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

@RequiredArgsConstructor
@Service
public class SellerService {
  private final AccountMapper accountDao;
  private final SellerMapper sellerDao;
  private final PasswordEncoder passwordEncoder;

  public Seller signup(SellerDto.Signup dto) {
    Seller seller = dto.toEntity(passwordEncoder);
    accountDao.save(seller);
    return sellerDao.save(seller);
  }

  public SellerDto.Read read(String loginId) {
    return sellerDao.findById(loginId).orElseThrow(()->new EntityNotFoundException("사용자를 찾을 수 없습니다")).toRead();
  }
}
