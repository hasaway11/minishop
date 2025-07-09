package com.example.demo.dao;

import com.example.demo.entity.product.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface ProductImageMapper {
  @Insert("insert into product_image values(product_image_seq.nextval, #{name}, #{productId})")
  int save(ProductImage productImage);

  @Select("select '${url}'||name from product_image where product_id=#{productId}")
  List<String> findImageUrlsByProductId(int productId, String url);
}
