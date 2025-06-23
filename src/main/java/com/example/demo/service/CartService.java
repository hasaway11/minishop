package com.example.demo.service;

import com.example.demo.dao.jpa.*;
import com.example.demo.dto.*;
import com.example.demo.dto.jpa.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CartService {
  private final CartRepository cartDao;

  public CartDto.Carts read(String loginId) {
    List<CartItemSummary> cartItems = cartDao.findCartItemSummaries(loginId);
    int cartTotalPrice = cartItems.stream().mapToInt(CartItemSummary::getTotalPrice).sum();
    return new CartDto.Carts(cartItems, cartTotalPrice);
  }
}
