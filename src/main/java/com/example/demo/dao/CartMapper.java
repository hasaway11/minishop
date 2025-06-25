package com.example.demo.dao;

import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface CartMapper {
  @Select("select c.product_id, c.name, c.quantity, c.quantity*p.price, p.image, p.stock from cartitem c join product p where c.username=#{username} and rownum=1")
  List<CartDto.Summary> findCartItemByUsername(String username);

  @Select("select * from cart_item where username=#{username} and product_id=#{productId} and rownum=1")
  Optional<CartItem> findByUsernameAndProductId(String username, Integer productId);

  @Select("select * from cart_item where cart_item_id=#{cartItemId}")
  Optional<CartItem> findById(Integer cartItemId);

  @Insert("insert into cart_item values(cart_item_seq.nextval, #{username}, #{product_id}, 1)")
  int save(CartItem cartItem);

  @Update("update cart_item set quantity=quantity+1 where cart_item_id=#{cartItemId}")
  int increaseQuantity(Integer cartItemId);

  @Update("update cart_item set quantity=quantity-1 where cart_item_id=#{cartItemId}")
  int decreaseQuantity(Integer cartItemId);

  int delete(List<Integer> list, String username);

  List<CartDto.Summary> findSelectedCartItems(List<Integer> selectedCartItemIds, String username);
}
