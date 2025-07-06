package com.example.demo.dao;

import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface OrderMapper {
  @SelectKey(statement="select orders_seq.nextval from dual", keyProperty="id", before=true, resultType=int.class)
  @Insert("insert into orders(id, username, order_at, status, order_total_price, address, zipcode) values(#{id}, #{username}, #{orderAt}, #{status}, #{orderTotalPrice}, #{address}, #{zipcode})")
  int save(Order order);

  List<OrderDto.Summary> findByUsername(String username);

  Optional<OrderDto.OrderDetail> findById(int id);
}
