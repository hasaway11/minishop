package com.example.demo.dao;

import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface OrderItemMapper {
  int save(List<OrderItem> orderItems);

  @Select("select oi.product_id, p.name, p.image, oi.quanity, oi.total_price from order_item oi join product p on oi.product_id=p.product_id where oi.order_id=#{orderId}")
  List<OrderDto.Item> findByOrderId(Integer orderId);
}
