package com.example.demo.dao;

import com.example.demo.entity.order.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface TempOrderMapper {
  // insert all로 변경할 것
  @Insert("insert into temp_orders values(temp_orders.seq.nextval, #{cartItemId}, #{username}, #{productId}, #{name}, #{image}, #{quanity}, #{totalPrice})")
  int save(TempOrder to);

  void saveAll(List<TempOrder> tempOrders);
}
