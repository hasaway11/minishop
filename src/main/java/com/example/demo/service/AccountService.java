package com.example.demo.service;

import com.example.demo.dao.jpa.*;
import com.example.demo.dto.*;
import com.example.demo.entity.account.*;
import com.example.demo.exception.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import lombok.*;
import org.apache.commons.lang3.*;
import org.springframework.mail.javamail.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class AccountService {
  private final AccountRepository accountDao;
  private final JavaMailSender mailSender;
  private final PasswordEncoder passwordEncoder;

  private void sendMail(String from, String to, String title, String text) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(title);
      helper.setText(text, true);
      mailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  @Transactional(readOnly=true)
  public boolean idAvailable(AccountDto.UsernameCheck dto) {
    return !accountDao.existsById(dto.getUsername());
  }


  public String sendVerificationCode(String email) {
    String checkCode = RandomStringUtils.secure().nextAlphanumeric(10);
    sendMail("admin@minishop.co.kr", email, "확인코드", checkCode);
    return checkCode;
  }

  @Transactional(readOnly=true)
  public Optional<String> findUseraname(String email) {
    return accountDao.findUsernameByEmail(email);
  }

  @Transactional
  public boolean resetPassword(AccountDto.ResetPassword dto) {
    Account account = accountDao.findById(dto.getUsername()).orElse(null);
    if(account == null)
      return false;
    String newPassword = RandomStringUtils.secure().nextAlphanumeric(10);
    account.changePassword(passwordEncoder.encode(newPassword));

    String content = "<p>아래 임시비밀번호로 로그인하세요</p>";
    content+= "<p>" + newPassword  + "</p>";
    sendMail("admin@icia.com", account.getEmail(), "임시비밀번호", content);
    return true;
  }

  public boolean checkPassword(String password, String loginId) {
    String encodedPassword = accountDao.findPasswordByUsername(loginId).orElseThrow(()->new EntityNotFoundException("사용자를 찾을 수 없습니다"));
    return (passwordEncoder.matches(password, encodedPassword));
  }
}
