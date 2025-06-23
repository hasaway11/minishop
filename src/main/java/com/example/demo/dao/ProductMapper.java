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

  @Select("select * from product where pno=#{pno}")
  Optional<Product> findById(int pno);

  Product save(Product product);

  @Delete("delete from product where pno=#{pno}")
  int delete(int pno);

  int update(ProductDto.Update dto);
}
