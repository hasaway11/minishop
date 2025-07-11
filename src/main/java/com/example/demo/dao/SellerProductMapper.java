package com.example.demo.dao;

import com.example.demo.dto.*;
import com.example.demo.entity.product.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface SellerProductMapper {
  @SelectKey(statement="select product_seq.nextval from dual", keyProperty="id", before=true, resultType=int.class)
  @Insert("insert into product values(#{id}, #{name}, #{info}, #{price}, 0, 0, 0, 0, #{stock}, #{category})")
  int save(Product product);

  List<ProductDto.SellerSummary> findAllBySeller(int pageno, int pagesize, String seller, String url);

  @Select("select count(*) from product where seller=#{seller}")
  int countBySeller(String seller);

  Optional<SellerProductDto.ProductDetail> findById(int productId, String url);
}
