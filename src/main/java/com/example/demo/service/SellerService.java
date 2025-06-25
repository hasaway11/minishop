package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.account.*;
import com.example.demo.exception.*;
import lombok.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@RequiredArgsConstructor
@Service
public class SellerService {
  private final AccountMapper accountDao;
  private final SellerMapper sellerDao;
  private final EmailVerificationMapper emailVerificationDao;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public Seller signup(SellerDto.Signup dto) {
    boolean isCodeExist = emailVerificationDao.existsByIdAndCode(dto.getEmail(), dto.getCode());
    if(!isCodeExist)
      throw new JobFailException("잘못된 확인코드입니다");
    if(accountDao.existsById(dto.getUsername()))
      throw new JobFailException("사용중인 아이디입니다");
    emailVerificationDao.deleteById(dto.getEmail());
    Seller seller = dto.toEntity(passwordEncoder);
    accountDao.save(seller);
    sellerDao.save(seller);
    return sellerDao.findById(dto.getUsername()).get();
  }

  public SellerDto.Read read(String loginId) {
    return sellerDao.findById(loginId).orElseThrow(()->new EntityNotFoundException("사용자를 찾을 수 없습니다")).toRead();
  }
}
