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
    List<CartDto.Summary> cartItems = cartDao.findByUsername(loginId, "http://localhost:8080/api/images/");
    int cartTotalPrice = cartItems.stream().mapToInt(CartDto.Summary::getTotalPrice).sum();
    return new CartDto.Carts(cartItems, cartTotalPrice);
  }

  private void stockCheckOrElseThrow(CartItem cartItem) {
    int stock = productDao.findStockById(cartItem.getProductId());
    if(cartItem.getQuantity()>=stock)
      throw new OutOfStockException(stock + "개 이상구입할 수 없습니다");
  }

  @Transactional(readOnly=true)
  public CartDto.Carts read(String loginId) {
    return getCart(loginId);
  }

  @Transactional
  public int addToCart(Integer productId, String loginId) {
    Optional<CartItem> optionalCartItem = cartDao.findByUsernameAndProductId(loginId, productId);
    if (optionalCartItem.isPresent()) {
      CartItem cartItem = optionalCartItem.get();
      stockCheckOrElseThrow(cartItem);
      cartDao.increaseQuantity(cartItem.getId());
      return cartItem.getQuantity()+1;
    } else {
      cartDao.save(CartItem.builder().username(loginId).productId(productId).build());
      return 1;
    }
  }

  public CartDto.Carts increaseQuantity(Integer cartItemId, String loginId) {
    Optional<CartItem> optionalCartItem = cartDao.findById(cartItemId);
    if(optionalCartItem.isEmpty())
      throw new JobFailException("작업을 수행할 수 없습니다");
    if(!optionalCartItem.get().getUsername().equals(loginId))
      throw new JobFailException("작업을 수행할 수 없습니다");
    stockCheckOrElseThrow(optionalCartItem.get());
    cartDao.increaseQuantity(cartItemId);
    return getCart(loginId);
  }

  public CartDto.Carts decreaseQuantity(int cartItemId, String loginId) {
    Optional<CartItem> optionalCartItem = cartDao.findById(cartItemId);
    if(optionalCartItem.isEmpty())
      throw new JobFailException("작업을 수행할 수 없습니다");
    if(!optionalCartItem.get().getUsername().equals(loginId))
      throw new JobFailException("작업 권한이 없습니다");
    if(optionalCartItem.get().getQuantity()<=1)
      throw new JobFailException("작업을 수행할 수 없습니다");
    cartDao.decreaseQuantity(cartItemId);
    return getCart(loginId);
  }

  public CartDto.Carts removeItem(int cartItemId, String loginId) {
    Optional<CartItem> optionalCartItem = cartDao.findById(cartItemId);
    if(optionalCartItem.isEmpty())
      throw new JobFailException("작업을 수행할 수 없습니다");
    if(!optionalCartItem.get().getUsername().equals(loginId))
      throw new JobFailException("작업 권한이 없습니다");
    cartDao.deleteById(cartItemId);
    return getCart(loginId);
  }

  // Oracle에서는 병렬로 DML(INSERT, UPDATE, DELETE)을 수행한 후 같은 트랜잭션 내에서 다시 그 테이블을 읽거나 수정하면 ORA-12838 오류 발생
  // (Cannot read/modify an object after modifying it in parallel)
  // @Transactional을 사용하면 안된다
  public CartDto.Carts removeItems(List<Integer> ids, String loginId) {
    List<CartItem> items = cartDao.findByIdIn(ids);
    boolean containsItemNotOwnedByUser = items.stream().anyMatch(item->!item.getUsername().equals(loginId));
    if (containsItemNotOwnedByUser)
      throw new JobFailException("작업 권한이 없는 항목이 포함되어 있습니다.");
    cartDao.deleteAll(ids);
    return getCart(loginId);
  }
}
