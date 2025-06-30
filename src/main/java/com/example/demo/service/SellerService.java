package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.account.*;
import com.example.demo.exception.*;
import com.example.demo.util.*;
import jakarta.validation.*;
import lombok.*;
import org.apache.commons.lang3.*;
import org.springframework.mail.javamail.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.validation.*;

import java.time.*;
import java.util.*;

@RequiredArgsConstructor
@Service
public class SellerService {
  private final AccountMapper accountDao;
  private final SellerMapper sellerDao;
  private final EmailVerificationMapper emailVerificationDao;
  private final PasswordEncoder passwordEncoder;
  private final EmailUtil emailUtil;

  @Transactional
  public Seller signup(SellerDto.Signup dto) {
    EmailVerification ev = emailVerificationDao.findByEmail(dto.getEmail()).orElseThrow(EmailVerificationRequireException::new);
    if(ev.getExpiresAt().isBefore(LocalDateTime.now())) {
      emailVerificationDao.deleteById(dto.getEmail());
      throw new EmailVerificationRequireException();
    }
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

  public void requestEmailVerification(SellerDto.EmailVerification dto) {
    if(emailVerificationDao.existsByEmail(dto.getEmail()))
      return;
    String code = RandomStringUtils.secure().nextAlphanumeric(10);
    emailUtil.sendMail("admin@minishop.com", dto.getEmail(), "확인코드", code);
    emailVerificationDao.save(new EmailVerification(dto.getEmail(), code));
  }

  // 확인코드가 맞는 지 확인
  public boolean checkVerifyCode(SellerDto.CheckVerifyCode dto) {
    EmailVerification ev = emailVerificationDao.findByCode(dto.getCode()).orElseThrow(()->new EntityNotFoundException("코드를 확인할 수 없습니다"));
    if(ev.getExpiresAt().isBefore(LocalDateTime.now()))
      return false;
    emailVerificationDao.checkVerify(ev.getEmail());
    return true;
  }
}
