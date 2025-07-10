package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.account.*;
import com.example.demo.exception.*;
import com.example.demo.util.*;

import lombok.*;
import org.apache.commons.io.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.*;

@RequiredArgsConstructor
@Service
public class MemberService {
  private final AccountMapper accountDao;
  private final MemberMapper memberDao;
  private final EmailVerificationMapper emailVerificationDao;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public Member signup(MemberDto.Signup dto) {
    if(accountDao.existsById(dto.getUsername()))
      throw new JobFailException("사용중인 아이디입니다");
    String profileName = ImageUtil.saveProfile(dto.getProfile(), dto.getUsername());
    Member member = dto.toEntity(passwordEncoder, profileName);
    accountDao.save(member);
    memberDao.save(member);
    return memberDao.findById(dto.getUsername()).get();
  }

  @Transactional(readOnly=true)
  public MemberDto.Read readme(String loginId) {
    return memberDao.findById(loginId).orElseThrow(()->new EntityNotFoundException("사용자를 찾을 수 없습니다")).toRead();
  }

  public String updateProfile(MemberDto.changeProfile dto, String loginId) {
    Member member = memberDao.findById(loginId).orElseThrow(()->new EntityNotFoundException("사용자를 찾을 수 없습니다"));
    MultipartFile file = dto.getProfile();
    String ext = FilenameUtils.getExtension(file.getOriginalFilename());
    String newProfileName = loginId + "." + ext;
    if(member.getProfile().equals(newProfileName)) {
      ImageUtil.saveProfile(dto.getProfile(), loginId);
    } else {
      memberDao.updateProfile(newProfileName, loginId);
      ImageUtil.saveProfile(dto.getProfile(), loginId);
      ImageUtil.deleteProfile(member.getProfile());
      return MiniShopConstants.IMAGE_URL + newProfileName;
    }
    // 업데이트 실패라면 새프사를 저장하지 않고 기존프사 유지
    return MiniShopConstants.IMAGE_URL + member.getProfile();
  }
}
