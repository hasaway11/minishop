package com.example.demo;

import com.example.demo.dao.*;
import com.example.demo.entity.account.*;
import org.junit.jupiter.api.*;
import org.mybatis.spring.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

@SpringBootTest
public class AccountMapperTest {
  @Autowired
  private SqlSessionTemplate tpl;

  @Test
  public void findByIdTest() {
    System.out.println(tpl);
  }
}
