package com.example.demo.dao;

import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface CartMapper {
  List<CartDto.Summary> findByUsername(String username, String url);

  @Select("select * from cart_item where username=#{username} and product_id=#{productId} and rownum=1")
  Optional<CartItem> findByUsernameAndProductId(String username, Integer productId);

  @Select("select * from cart_item where id=#{id}")
  Optional<CartItem> findById(int id);

  @SelectKey(statement="select cart_item_seq.nextval from dual", keyProperty="id", before=true, resultType=int.class)
  @Insert("insert into cart_item values(cart_item_seq.nextval, #{username}, #{productId}, 1)")
  int save(CartItem cartItem);

  @Update("update cart_item set quantity=quantity+1 where id=#{id}")
  int increaseQuantity(int id);

  @Update("update cart_item set quantity=quantity-1 where id=#{id}")
  int decreaseQuantity(int id);

  List<CartDto.Summary> findSelectedCartItems(List<Integer> selectedCartItemIds, String username);

  @Delete("delete from cart_item where id=#{id}")
  int deleteById(int id);

  List<CartItem> findByIdIn(List<Integer> ids);

  int deleteAll(List<Integer> ids);
}
