package com.example.demo.dao;

import com.example.demo.entity.account.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface SellerMapper {
  @Insert("insert into seller values(#{username}, #{companyName}, #{representative}, #{address}, #{sellerLevel}, #{salesCount}, #{salesAmount})")
  int save(Seller seller);

  @Select("select a.*, s.company_name, s.representative, s.address, s. seller_level, s.sales_count, s.sales_amount from account a join seller s on a.username=s.username where s.username=#{username}")
  Optional<Seller> findById(String username);
}
