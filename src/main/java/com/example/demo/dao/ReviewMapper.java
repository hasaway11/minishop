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
}
