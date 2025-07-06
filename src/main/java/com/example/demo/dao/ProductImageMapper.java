package com.example.demo.dao;

import com.example.demo.entity.product.*;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProductImageMapper {
  @Insert("insert into product_image values(product_image_seq.nextval, #{name}, #{productId})")
  void save(ProductImage productImage);
}
