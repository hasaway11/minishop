package com.example.demo;

import com.example.demo.dao.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

@SpringBootTest
public class CartMapperTest {
  @Autowired
  private CartMapper dao;

  @Test
  public void test1() {
    dao.findSelectedCartItems(Arrays.asList(1,3,5));
  }
}
