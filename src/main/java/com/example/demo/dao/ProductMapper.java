package com.example.demo.dao;

import com.example.demo.dto.*;
import com.example.demo.entity.product.*;
import jakarta.validation.constraints.*;
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
  int save(Product product);

  int update(ProductDto.Update dto);

  @Select("select stock from product where id=#{id}")
  int findStockById(int id);

  @Update("update product set sales_count=sales_count+#{count}, sales_amount=sales_amount+#{money} where id=#{productId}")
  int updateSalesStat(int productId, int count, int money);

  @Update("update product set review_count=review_count+1, total_star=total_star+#{rating} where id=#{productId}")
  int updateRating(Integer productId, Integer rating);
}
