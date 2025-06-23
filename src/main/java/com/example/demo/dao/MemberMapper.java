package com.example.demo.dao;

import com.example.demo.entity.account.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface MemberMapper {
  @Insert("insert into member values(#{username}, #{birthDate}, #{profile}, #{memberLevel})")
  Member save(Member member);

  @Select("select a.*, m.* from account a join member m on a.username=m.username where m.username=#{username}")
  Optional<Member> findById(String username);

  @Update("update member set profile=#{newProfile} where username=#{username}")
  int updateProfile(String newProfile, String loginId);
}
