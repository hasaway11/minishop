package com.example.demo.dao;

import com.example.demo.dto.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface SellerOrderMapper {
  List<SellerOrderDto.OrderDetail> findBySeller(String seller, String url);

  int updateStatus(List<SellerOrderDto.OrderItemId> list);
}
