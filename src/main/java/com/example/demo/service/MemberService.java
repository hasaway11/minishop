package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.account.*;
import com.example.demo.exception.*;
import com.example.demo.util.*;
import jakarta.annotation.*;
import jakarta.validation.*;
import lombok.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.io.*;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MemberService {
  private final MemberRepository memberDao;
  private final PasswordEncoder passwordEncoder;

  @PostConstruct
  public void init() {
    FunctionUtil.initDefaultProfile();
  }

  public Member signup(MemberDto .Signup dto) {
    Member member = dto.toEntity(passwordEncoder);
    return memberDao.save(member);
  }

  @Transactional(readOnly=true)
  public MemberDto.Read read(String loginId) {
    return memberDao.findById(loginId).orElseThrow(()->new EntityNotFoundException("사용자를 찾을 수 없습니다")).toRead();
  }

  public boolean changePassword(MemberDto.PasswordChange dto, String loginId) {
    String encodedPassword = memberDao.findPasswordByUsername(loginId);
    if(!passwordEncoder.matches(dto.getCurrentPassword(), encodedPassword))
      return false;
    return memberDao.updatePassword(loginId, passwordEncoder.encode(dto.getNewPassword()))==1;
  }

  public boolean changeProfile(MemberDto.changeProfile dto, String loginId) {
    Optional<String> result = FunctionUtil.getProfile(dto.getProfile(), true);
    return result.filter(s -> memberDao.updateProfile(s, loginId) == 1).isPresent();
  }
}
