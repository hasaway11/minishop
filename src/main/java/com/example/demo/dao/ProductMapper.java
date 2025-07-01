package com.example.demo.dao;

import com.example.demo.dto.*;
import com.example.demo.entity.product.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface ProductMapper {
  List<ProductDto.Summary> findAllBySeller(int pageno, int pagesize, String seller);

  List<ProductDto.Summary> findAll(int pageno, int pagesize);

  @Select("select count(*) from product where seller=#{seller}")
  int countBySeller(String seller);

  @Select("select count(*) from product")
  int count();

  @Select("select * from product where id=#{id}")
  Optional<Product> findById(int id);

  @SelectKey(statement="select product_seq.nextval from dual", keyProperty="id", before=true, resultType=int.class)
  @Insert("insert into product values(#{id}, #{name}, #{info}, #{price}, 0, 0, 0, 0, #{stock}, #{category})")
  void save(Product product);

  @Delete("delete from product where id=#{id}")
  int deleteById(int id);

  int update(ProductDto.Update dto);

  @Select("select case where stock>0 then true else false end from product where id=#{id}")
  boolean hasStock(int productId);
}
