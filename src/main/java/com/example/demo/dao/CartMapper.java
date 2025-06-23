package com.example.demo.dao;

import com.example.demo.dto.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface CartMapper {
  @Select("select c.pno, c.name, c.quantity, c.quantity*p.price, p.image  from cartitem c join product p where c.username=#{username} and rownum=1")
  List<CartDto.Summary> findCartItemByUsername(String username);
}
