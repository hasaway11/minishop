package com.example.demo.dao;

import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface SellerOrderMapper {
  List<SellerOrderDto.OrderSummary> findBySeller(String seller);

  int updateStatus(List<Integer> orderItemList, OrderStatus status);
}
