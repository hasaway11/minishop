package com.example.demo.dao;

import com.example.demo.entity.product.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface ProductImageMapper {
  @Select("select name from product_image where product_id=#{productId}")
  List<String> findByProductId(int productId);

  @Insert("insert into product_image values(product_image_seq.nextvale, #{name}, #{productId})")
  void save(ProductImage productImage);

  @Delete("delete from product_iamge where product_id=#{productId}")
  void deleteByProductId(int productId);
}
