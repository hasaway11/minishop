package com.example.demo;

import com.example.demo.dao.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

@SpringBootTest
public class CategroyMapperTest {
  @Autowired
  private CategoryMapper dao;

  //@Test
  public void testTest() {
    dao.findAll().forEach(System.out::println);
  }

  @Test
  public void testTest2() {
    dao.findByMinorCategory().forEach(System.out::println);
  }
}
