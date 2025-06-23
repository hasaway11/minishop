package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CartService {
  private final CartMapper cartDao;

  public CartDto.Carts read(String loginId) {
    List<CartDto.Summary> cartItems = cartDao.findCartItemByUsername(loginId);
    int cartTotalPrice = cartItems.stream().mapToInt(CartDto.Summary::getTotalPrice).sum();
    return new CartDto.Carts(cartItems, cartTotalPrice);
  }

  @Transactional
  public boolean addToCart(Integer pno, String loginId) {
    return false;
  }

}
