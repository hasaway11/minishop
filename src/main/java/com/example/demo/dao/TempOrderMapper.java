package com.example.demo.dao;

import com.example.demo.entity.order.*;
import org.apache.ibatis.annotations.*;

import java.time.*;
import java.util.*;

@Mapper
public interface TempOrderMapper {
  int saveAll(List<TempOrder> tempOrders, int tempId);

  int deleteByTempId(int tempId);

  @Select("select * from temp_orders where temp_id=#{tempId}")
  List<TempOrder> findByTempId(Integer tempId);

  @Select("select temp_orders_seq.nextval from dual")
  int findNextTempId();

  @Delete("delete from temp_orders where invalid_at<#{now}")
  int deleteExpiredOrders(LocalDateTime now);
}
