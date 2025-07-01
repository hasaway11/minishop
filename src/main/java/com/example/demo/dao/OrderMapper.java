package com.example.demo.dao;

import com.example.demo.entity.order.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface OrderMapper {
  @SelectKey(statement="select orders_seq.nextval from dual", keyProperty="id", before=true, resultType=int.class)
  @Insert("insert into orders(id, username, order_date, status, order_total_price) values(#{id}, #{username}, #{orderDate}, #{status}, #{orderTotalPrice})")
  int save(Order order);

  @Select("select * from orders where username=#{username} order by id desc")
  List<Order> findByUsername(String username);

  @Update("update orders set address=#{address} where id=#{id}")
  void updateAddress(int id, String address);

  @Update("update orders set status=#{status} where id=#{id}")
  void updateStatus(int id, OrderStatus status);

  @Select("select * from orders where id=#{id} and username=#{username}")
  Optional<Order> findByIdAndUsername(int id, String loginId);
}
