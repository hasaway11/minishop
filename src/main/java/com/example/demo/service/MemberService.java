package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.account.*;
import com.example.demo.exception.*;
import com.example.demo.util.*;
import jakarta.annotation.*;
import lombok.*;
import org.apache.commons.io.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class MemberService {
  private final AccountMapper accountDao;
  private final MemberMapper memberDao;
  private final EmailVerificationMapper emailVerificationDao;
  private final PasswordEncoder passwordEncoder;


  @Transactional
  public Member signup(MemberDto.Signup dto) {
    boolean isCodeExist = emailVerificationDao.existsByIdAndCode(dto.getEmail(), dto.getCode());
    if(!isCodeExist)
      throw new JobFailException("잘못된 확인코드입니다");
    if(accountDao.existsById(dto.getUsername()))
      throw new JobFailException("사용중인 아이디입니다");
    Member member = dto.toEntity(passwordEncoder);
    String profileName = ImageUtil.saveProfile(dto.getProfile(), dto.getUsername());
    member.setProfile(profileName);
    accountDao.save(member);
    memberDao.save(member);
    emailVerificationDao.deleteById(dto.getEmail());
    return memberDao.findById(dto.getUsername()).get();
  }

  @Transactional(readOnly=true)
  public MemberDto.Read read(String loginId) {
    return memberDao.findById(loginId).orElseThrow(()->new EntityNotFoundException("사용자를 찾을 수 없습니다")).toRead();
  }

  public boolean changeProfile(MemberDto.changeProfile dto, String loginId) {
    Member member = memberDao.findById(loginId).get();
    MultipartFile file = dto.getProfile();
    String ext = FilenameUtils.getExtension(file.getOriginalFilename());
    String newProfileName = loginId + "." + ext;
    if(member.getProfile().equals(newProfileName)) {
      프사덮어쓰기;
      return;
    }
    if(memberDao.updateProfile(newProfileName, loginId)==1) {
      새프사저장;
      기존프사삭제;
    }
    // 업데이트 실패라면 새프사를 저장하지 않는다. 따라서 기존프사 유지
  }
}
