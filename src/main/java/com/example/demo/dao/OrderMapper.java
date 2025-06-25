package com.example.demo.dao;

import com.example.demo.entity.order.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface OrderMapper {
  @SelectKey(statement="select orders_seq.nextval from dual", keyProperty="orderId", before=true, resultType=int.class)
  @Insert("insert into orders(order_id, username, order_date, status, order_total_price) values(#{orderId}, #{username}, #{orderDate}, #{status}, #{orderTotalPrice})")
  int save(Order order);

  @Select("select * from orders where username=#{username} order by order_id desc")
  List<Order> findByUsername(String username);

  @Update("update orders set address=#{address}, status='PAY' where order_id=#{orderId}")
  void updateOrder(int orderId, String address);

  @Select("select * from orders where order_id=#{orderId} and username=#{username}")
  Optional<Order> findByIdAndUsername(Integer orderId, String loginId);
}
