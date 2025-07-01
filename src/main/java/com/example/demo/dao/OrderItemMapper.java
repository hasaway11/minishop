package com.example.demo.dao;

import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface OrderItemMapper {
  int save(List<OrderItem> orderItems);

  @Select("select oi.product_id, p.name, p.image, oi.quanity, oi.total_price from order_item oi join product p on oi.product_id=p.product_id where oi.id=#{id}")
  List<OrderDto.Item> findById(int id);

  @Select("select * from order_item where order_id in (select id from orders where username=#{username}) and review_writable=1")
  List<OrderItem> findWritableList(String username);
}
