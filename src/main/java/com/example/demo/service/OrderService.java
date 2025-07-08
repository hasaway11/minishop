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
public class OrderService {
  private final CartMapper cartDao;
  private final TempOrderMapper tempOrderDao;
  private final OrderMapper orderDao;
  private final OrderItemMapper orderItemDao;
  private final ProductMapper productDao;
  private final MemberMapper memberDao;

  @Transactional
  public int prepareOrder(List<Integer> selectedCartItemIds, String loginId) {
    // 장바구니에서 선택한 상품들의 임시주문엔티티를 읽어온다
    // 자신의 주문상품이 아닌 상품이 섞여 있다면 오류 처리
    // 임시주문엔티티들을 저장한 다음 임시주문번호를 리턴
    List<TempOrder> tempOrders =  cartDao.findSelectedCartItems(selectedCartItemIds, "http://localhost:8080/api/images/");
    boolean containsItemNotOwnedByUser = tempOrders.stream().anyMatch(item->!item.getUsername().equals(loginId));
    if (containsItemNotOwnedByUser)
      throw new JobFailException("잘못된 작업입니다.");
    int newTempId = tempOrderDao.findNextTempId();
    tempOrderDao.saveAll(tempOrders, newTempId);
    return newTempId;
  }

  // 임시 주문 엔티티와 총 주문 가격을 리턴
  @Transactional(readOnly = true)
  public Map<String,Object> read(Integer tempId, String loginId) {
    List<TempOrder> tempOrders = tempOrderDao.findByTempId(tempId);
    if(tempOrders.isEmpty())
      throw new JobFailException("주문 정보를 찾을 수 없습니다");
    boolean containsItemNotOwnedByUser = tempOrders.stream().anyMatch(item->!item.getUsername().equals(loginId));
    if (containsItemNotOwnedByUser)
      throw new JobFailException("이미 주문완료되었거나 유효하지 않은 주문서입니다.");
    int orderTotalPrice = tempOrders.stream().mapToInt(TempOrder::getTotalPrice).sum();
    return Map.of("orderTotalPrice", orderTotalPrice, "orders", tempOrders);
  }

  @Transactional
  public void order(OrderDto.OrderRequest dto, String loginId) {
    List<TempOrder> tempOrders = tempOrderDao.findByTempId(dto.getId());
    if(tempOrders.isEmpty())
      throw new JobFailException("주문 정보를 찾을 수 없습니다");
    boolean containsItemNotOwnedByUser = tempOrders.stream().anyMatch(item->!item.getUsername().equals(loginId));
    if (containsItemNotOwnedByUser)
      throw new JobFailException("이미 주문완료되었거나 유효하지 않은 주문서입니다.");

    int orderTotalPrice = tempOrders.stream().mapToInt(TempOrder::getTotalPrice).sum();
    Order order = new Order(loginId, dto.getZipcode(), dto.getAddress(), orderTotalPrice);
    orderDao.save(order);

    List<OrderItem> list = tempOrders.stream().map(tempOrder->new OrderItem(order.getId(), tempOrder)).toList();
    tempOrders.forEach(item->{
      orderItemDao.save(new OrderItem(order.getId(), item));
      productDao.updateSalesStat(item.getProductId(), item.getQuantity(), item.getTotalPrice());
    });

    List<Integer> cartIds = tempOrders.stream().map(TempOrder::getCartItemId).toList();
    tempOrderDao.deleteByTempId(dto.getId());
    cartDao.deleteAll(cartIds);
    memberDao.updateOrderStat(orderTotalPrice, loginId);
  }

  @Transactional(readOnly = true)
  public List<OrderDto.Summary> orderList(String loginId) {
    return orderDao.findByUsername(loginId);
  }

  @Transactional(readOnly = true)
  public OrderDto.OrderDetail orderDetails(Integer orderId, String loginId) {
    Optional<OrderDto.OrderDetail> optionalOrder = orderDao.findById(orderId);
    if(optionalOrder.isEmpty())
      throw new JobFailException("주문내역을 확인할 수 없습니다");
    OrderDto.OrderDetail orderDetail = optionalOrder.get();
    List<OrderDto.Item> orderItems = orderItemDao.findByOrderId(orderId, "http://localhost:8080/api/images/");
    orderDetail.setOrderItems(orderItems);
    return orderDetail;
  }
}
