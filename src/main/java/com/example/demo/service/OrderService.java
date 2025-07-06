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

  @Transactional
  public int prepareOrder(List<Integer> selectedCartItemIds, String loginId) {
    List<TempOrder> tempOrders =  cartDao.findSelectedCartItems(selectedCartItemIds, "http://localhost:8080/api/images/");
    boolean containsItemNotOwnedByUser = tempOrders.stream().anyMatch(item->!item.getUsername().equals(loginId));
    if (containsItemNotOwnedByUser)
      throw new JobFailException("잘못된 작업입니다.");
    int newTempId = tempOrderDao.findNextTempId();
    tempOrderDao.saveAll(tempOrders, newTempId);
    return newTempId;
  }

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

    List<OrderItem> list = tempOrders.stream().map(t->new OrderItem(order.getId(), t.getProductId(), t.getQuantity(), t.getTotalPrice(), true)).toList();
    orderItemDao.save(list);

    List<Integer> cartIds = tempOrders.stream().map(TempOrder::getCartItemId).toList();
    tempOrderDao.deleteByTempId(dto.getId());
    cartDao.deleteAll(cartIds);
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
