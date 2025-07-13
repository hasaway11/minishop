package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.account.*;
import com.example.demo.exception.*;
import com.example.demo.util.*;

import lombok.*;
import org.apache.commons.lang3.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class AccountService {
  private final AccountMapper accountDao;
  private final EmailUtil emailUtil;
  private final EmailVerificationMapper emailVerificationDao;
  private final PasswordEncoder passwordEncoder;

  @Transactional(readOnly=true)
  public boolean checkUsernameAvailability(AccountDto.UsernameCheck dto) {
    return !accountDao.existsById(dto.getUsername());
  }

  @Transactional(readOnly=true)
  public Optional<String> findUseranameByEmail(String email) {
    return accountDao.findUsernameByEmail(email);
  }

  @Transactional
  public boolean issueTemporaryPassword(AccountDto.PasswordReset dto) {
    Account account = accountDao.findById(dto.getUsername()).orElse(null);
    if(account==null || !account.getEmail().equals(dto.getEmail()))
      return false;
    String newPassword = RandomStringUtils.secure().nextAlphanumeric(10);
    accountDao.updatePassword(dto.getUsername(), passwordEncoder.encode(newPassword));

    String content = "<p>아래 임시비밀번호로 로그인하세요</p>";
    content+= "<p>" + newPassword  + "</p>";
    emailUtil.sendMail("admin@icia.com", account.getEmail(), "임시비밀번호", content);
    return true;
  }

  public boolean verifyPassword(String password, String loginId) {
    String encodedPassword = accountDao.findPasswordByUsername(loginId).orElseThrow(()->new EntityNotFoundException("사용자를 찾을 수 없습니다"));
    return passwordEncoder.matches(password, encodedPassword);
  }

  public boolean updatePassword(AccountDto.PasswordChange dto, String loginId) {
    String encodedPassword = accountDao.findPasswordByUsername(loginId).orElseThrow(()->new EntityNotFoundException("사용자를 찾을 수 없습니다"));
    if(!passwordEncoder.matches(dto.getCurrentPassword(), encodedPassword))
      return false;
    return accountDao.updatePassword(loginId, passwordEncoder.encode(dto.getNewPassword()))==1;
  }
}
