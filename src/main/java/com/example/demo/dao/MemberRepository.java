package com.example.demo.dao;

import com.example.demo.entity.account.*;
import org.springframework.data.jpa.repository.*;

public interface MemberRepository extends JpaRepository<Member, String> {
  @Query("select m.password from Member m where m.username=:username")
  String findPasswordByUsername(String username);

  @Modifying
  @Query("update Member m set m.password=:password where m.username=:username")
  int updatePassword(String username, String password);

  @Modifying
  @Query("update Member m set m.profile=:profile where m.username=:username")
  int updateProfile(String profile, String username);
}
