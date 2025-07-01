package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import com.example.demo.exception.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CartService {
  private final CartMapper cartDao;
  private final ProductMapper productDao;

  private CartDto.Carts getCart(String loginId) {
    List<CartDto.Summary> cartItems = cartDao.findByUsername(loginId);
    int cartTotalPrice = cartItems.stream().mapToInt(CartDto.Summary::getTotalPrice).sum();
    return new CartDto.Carts(cartItems, cartTotalPrice);
  }

  private void stockCheckOrElseThrow(Integer productId) {
    if(!productDao.hasStock(productId))
      throw new OutOfStockException();
  }

  @Transactional(readOnly=true)
  public CartDto.Carts read(String loginId) {
    return getCart(loginId);
  }

  @Transactional
  public CartDto.Carts addToCart(Integer productId, String loginId) {
    // 고객 장바구니에 상품이 들어있으면 개수 증가, 없으면 추가
    stockCheckOrElseThrow(productId);
    Optional<CartItem> cartItem = cartDao.findByUsernameAndProductId(loginId, productId);
    if (cartItem.isPresent())
      cartDao.increaseQuantity(cartItem.get().getCartItemId());
    else
      cartDao.save(CartItem.builder().username(loginId).productId(productId).build());
    return getCart(loginId);
  }

  public CartDto.Carts increase(Integer cartItemId, String loginId) {
    Optional<CartItem> cartItem = cartDao.findById(cartItemId);
    if(cartItem.isEmpty())
      throw new JobFailException("작업을 수행할 수 없습니다");
    stockCheckOrElseThrow(cartItem.get().getProductId());
    cartDao.increaseQuantity(cartItemId);
    return getCart(loginId);
  }

  public CartDto.Carts decrease(Integer cartItemId, String loginId) {
    Optional<CartItem> cartItem = cartDao.findById(cartItemId);
    if(cartItem.isEmpty())
      throw new JobFailException("작업을 수행할 수 없습니다");
    if(cartItem.get().getQuantity()>1)
      throw new JobFailException("작업을 수행할 수 없습니다");
    cartDao.decreaseQuantity(cartItemId);
    return getCart(loginId);
  }

  public CartDto.Carts delete(CartDto.Delete dto, String loginId) {
    cartDao.deleteByIds(dto.getIds(), loginId);
    return getCart(loginId);
  }
}
