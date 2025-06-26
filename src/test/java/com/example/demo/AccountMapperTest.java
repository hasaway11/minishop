package com.example.demo;

import com.example.demo.dao.*;
import com.example.demo.entity.account.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

@SpringBootTest
public class AccountMapperTest {
  @Autowired
  private AccountMapper accountDao;

  @Test
  public void findByIdTest() {
    Optional<Account> account = accountDao.findById("spring");
    System.out.println(account.isEmpty());
  }
}
