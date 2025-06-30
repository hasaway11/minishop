package com.example.demo.dao;

import com.example.demo.entity.account.*;
import jakarta.validation.constraints.*;
import org.apache.ibatis.annotations.*;

import java.time.*;
import java.util.*;

@Mapper
public interface EmailVerificationMapper {
  @Insert("insert into email_verification values(#{email}, #{code}, #{sendAt}, #{expiresAt}, #{isVerify})")
  void save(EmailVerification emailVerification);

  @Delete("delete from email_verification where email=#{email}")
  int deleteById(String email);

  @Update("update email_verification set is_verify=1 where email=#{email}")
  boolean checkVerify(String email);

  @Select("select * from email_verification where code=#{code} and rownum=1")
  Optional<EmailVerification> findByCode(String code);

  @Select("select * from email_verification where email=#{email}")
  Optional<EmailVerification> findByEmail(String email);

  @Select("select count(*) from email_verification where email=#{email} and rownum=1")
  boolean existsByEmail(String email);
}
