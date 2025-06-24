package com.example.demo.dao;

import com.example.demo.entity.order.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface OrderMapper {
  @SelectKey(statement="select orders_seq.nextval from dual", keyProperty="orderId", before=true, resultType=int.class)
  @Insert("insert into orders values(#{orderId}, #{username}, #{orderDate}, #{deliveryAddress}, #{status}, #{orderTotalPrice})")
  int save(Order order);

  @Select("select * from orders where username=#{username} order by order_id desc")
  List<Order> findByUsername(String username);
}
