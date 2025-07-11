package com.example.demo.dao;

import com.example.demo.dto.*;
import com.example.demo.entity.product.*;
import jakarta.validation.constraints.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface ProductMapper {
  List<ProductDto.Summary> findAll(int pageno, int pagesize, String seller, String url);

  int count(String seller);

  Optional<ProductDto.Read> findById(int id, String url);

  int update(ProductDto.Update dto);

  @Select("select stock from product where id=#{id}")
  int findStockById(int id);

  @Update("update product set sales_count=sales_count+#{count}, sales_amount=sales_amount+#{money} where id=#{productId}")
  int updateSalesStat(int productId, int count, int money);

  @Update("update product set review_count=review_count+#{count}, total_rating=total_rating+#{rating} where id=#{productId}")
  int updateRating(Integer productId, Integer count, Integer rating);
}
