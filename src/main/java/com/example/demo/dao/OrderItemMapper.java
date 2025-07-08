package com.example.demo.dao;

import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import jakarta.validation.constraints.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface OrderItemMapper {
  int save(OrderItem orderItem);

  List<OrderDto.Item> findByOrderId(int orderId, String url);

  Optional<OrderDto.OrderProductDto> findProductByOrderItemId(int orderItemId, String url);

  @Select("select * from order_item where id=#{orderItemId}")
  Optional<OrderItem> findById(Integer orderItemId);

  @Update("update order_item set review_writable=0 where id=#{orderItemId}")
  int updateReviewableToFalse(Integer orderItemId);
}
