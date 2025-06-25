package com.example.demo.dao;

import jakarta.validation.constraints.*;
import org.apache.ibatis.annotations.*;

import java.time.*;

@Mapper
public interface EmailVerificationMapper {
  @Insert("insert into email_verification values(#{email}, #{checkCode}, #{sendAt}, #{expiresAt})")
  void save(String email, String checkCode, LocalDateTime sendAt, LocalDateTime expiresAt);

  @Select("select count(*) from email_verification where email=#{email} and code=#{code} and rownum=1")
  boolean existsByIdAndCode(String email, String code);

  @Delete("delete from email_verification where email=#{email}")
  int deleteById(String email);
}
