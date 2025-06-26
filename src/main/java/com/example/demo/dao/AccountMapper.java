package com.example.demo.dao;


import com.example.demo.entity.account.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface AccountMapper {
  @Select("select count(*) from account where username=#{username} and rownum=1")
  boolean existsById(String username);

  @Insert("insert into account values(#{username}, #{password}, #{email}, #{signupDate})")
  int save(Account account);

  @Select("select password from account where email=#{email} and rownum=1")
  Optional<String> findUsernameByEmail(String email);

  @Select("select username, password, email from account where username=#{username}")
  Optional<Account> findById(String username);

  @Select("select password from account where username=#{username}")
  Optional<String> findPasswordByUsername(String loginId);

  @Update("update account set password=#{newPassword} where username=#{username}")
  int updatePassword(String username, String newPassword);
}
