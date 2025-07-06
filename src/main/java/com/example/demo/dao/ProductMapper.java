package com.example.demo.dao;

import com.example.demo.dto.*;
import com.example.demo.entity.product.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface ProductMapper {
  List<ProductDto.Summary> findAllBySeller(int pageno, int pagesize, String seller, String url);

  List<ProductDto.Summary> findAll(int pageno, int pagesize, String url);

  @Select("select count(*) from product where seller=#{seller}")
  int countBySeller(String seller);

  int count(String seller);

  Optional<ProductDto.Read> findById(int id, String url);

  @SelectKey(statement="select product_seq.nextval from dual", keyProperty="id", before=true, resultType=int.class)
  @Insert("insert into product values(#{id}, #{name}, #{info}, #{price}, 0, 0, 0, 0, #{stock}, #{category})")
  void save(Product product);

  int update(ProductDto.Update dto);

  @Select("select stock from product where id=#{id}")
  int findStockById(int id);
}
