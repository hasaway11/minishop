package com.example.demo.dao.jpa;

import com.example.demo.dto.jpa.*;
import com.example.demo.entity.order.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.*;

public interface CartRepository extends JpaRepository<CartItem, CartItemId> {
  @Query("select new com.example.demo.dto.jpa.CartItemSummary(c.id.pno, p.name, c.quantity, c.quantity*p.price , p.image) from CartItem c join c.product p where c.id.username = :username")
  List<CartItemSummary> findCartItemSummaries(@Param("username") String username);
}
