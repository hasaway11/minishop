package com.example.demo.dao;

import com.example.demo.dto.*;
import com.example.demo.entity.product.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface ReviewMapper {
  @Insert("insert into review values(review_seq.nextval, #{writer}, #{content}, #{rating}, #{writeTime}, #{productId})")
  int save(Review review);

  List<ReviewDto.Read> findByWriter(String writer, String url);

  @Select("select * from review where id=#{reviewId}")
  Optional<Review> findById(int reviewId);

  @Delete("delete from review where id=#{reviewId}")
  int deleteById(int reviewId);

  @Select("select * from review where product_id=#{productId}")
  List<Review> findByProductId(Integer productId);
}
