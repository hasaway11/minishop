package com.example.demo.dao.jpa;

import com.example.demo.entity.account.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface AccountRepository extends JpaRepository<Account, String> {
  @Query("select a.username from Account a where a.email=:email")
  Optional<String> findUsernameByEmail(String email);

  @Query("select a.password from Account a where a.username=:username")
  Optional<String> findPasswordByUsername(String username);
}
