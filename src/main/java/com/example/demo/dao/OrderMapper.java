package com.example.demo.dao;

import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface OrderMapper {
  @SelectKey(statement="select orders_seq.nextval from dual", keyProperty="id", before=true, resultType=int.class)
  @Insert("insert into orders(id, username, order_at, status, order_total_price) values(#{id}, #{username}, #{orderAt}, #{status}, #{orderTotalPrice})")
  int save(Order order);

  List<OrderDto.Summary> findByUsername(String username);

  @Update("update orders set address=#{address}, zipcode=#{zipcode} where id=#{id}")
  int updateAddress(int id, String address, String zipcode);

  @Update("update orders set status=#{status} where id=#{id}")
  int updateStatus(int id, OrderStatus status);

  @Select("select * from orders where id=#{id}")
  Optional<Order> findById(int id);

  Optional<OrderDto.OrderDetail> findByIdWithDetail(int id);
}
