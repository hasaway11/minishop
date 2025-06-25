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

  @Select("select * from product where product_id=#{productId}")
  Optional<Product> findById(int pno);

  @SelectKey(statement="select product_seq.nextval from dual", keyProperty="productId", before=true, resultType=int.class)
  @Insert("insert into product values(#{productId}, #{name}, #{info}, #{price}, 0, 0, 0, 0, #{stock}, #{category})")
  Product save(Product product);

  @Delete("delete from product where product_id=#{productId}")
  int delete(int pno);

  int update(ProductDto.Update dto);

  @Select("select case where stock>0 then true else false end from product where product_id=#{productId}")
  boolean hasStock(Integer productId);
}
