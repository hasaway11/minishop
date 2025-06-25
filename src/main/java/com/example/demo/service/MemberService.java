package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.account.*;
import com.example.demo.exception.*;
import com.example.demo.util.*;
import jakarta.annotation.*;
import lombok.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class MemberService {
  private final AccountMapper accountMapper;
  private final MemberMapper memberDao;
  private final PasswordEncoder passwordEncoder;


  @Transactional
  public Member signup(MemberDto.Signup dto) {
    Member member = dto.toEntity(passwordEncoder);
    String profileName = ImageUtil.saveProfile(dto.getProfile(), dto.getUsername());
    member.setProfile(profileName);
    accountMapper.save(member);
    return memberDao.save(member);
  }

  @Transactional(readOnly=true)
  public MemberDto.Read read(String loginId) {
    return memberDao.findById(loginId).orElseThrow(()->new EntityNotFoundException("사용자를 찾을 수 없습니다")).toRead();
  }

  public boolean changeProfile(MemberDto.changeProfile dto, String loginId) {
    String newProfile = ImageUtil.saveProfile(dto.getProfile(), loginId);
    return memberDao.updateProfile(newProfile, loginId) == 1;
  }
}
