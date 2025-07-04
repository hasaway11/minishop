package com.example.demo.dao;

import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface OrderItemMapper {
  int save(List<OrderItem> orderItems);

  List<OrderDto.Item> findByOrderId(int orderId, String url);

  @Select("select * from order_item where order_id in (select id from orders where username=#{username}) and review_writable=1")
  List<OrderItem> findWritableList(String username);
}
