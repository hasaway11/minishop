package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import com.example.demo.exception.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;
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
    List<TempOrder> tempOrders =  cartDao.findSelectedCartItems(selectedCartItemIds);
    boolean containsItemNotOwnedByUser = selectedCartItems.stream().anyMatch(item->!item.getUsername().equals(loginId));
    if (containsItemNotOwnedByUser)
      throw new JobFailException("잘못된 작업입니다.");
    int orderTotalPrice = selectedCartItems.stream().mapToInt(TempOrder::getTotalPrice).sum();
    tempOrderDao.saveAll(tempOrders);
    return new tempOrder
  }

  //  @Transactional
//  public int prepareOrder(List<Integer> selectedCartItemIds, String loginId) {
//    List<CartDto.CheckoutDto> selectedCartItems =  cartDao.findSelectedCartItems(selectedCartItemIds);
//    boolean containsItemNotOwnedByUser = selectedCartItems.stream().anyMatch(item->!item.getUsername().equals(loginId));
//    if (containsItemNotOwnedByUser)
//      throw new JobFailException("잘못된 작업입니다.");
//    int orderTotalPrice = selectedCartItems.stream().mapToInt(CartDto.CheckoutDto::getTotalPrice).sum();
//
//    Order order = Order.builder().username(loginId).orderAt(LocalDateTime.now()).status(OrderStatus.CREATE).orderTotalPrice(orderTotalPrice).build();
//    orderDao.save(order);
//
//    List<OrderItem> orderItems = selectedCartItems.stream().map(item->new OrderItem(order.getId(), item)).toList();
//    orderItemDao.save(orderItems);
//    return order.getId();
//  }

  public OrderDto.OrderDetail read(Integer orderId, String loginId) {
    Order order =  orderDao.findById(orderId).orElseThrow(()->new JobFailException("작업을 수행할 수 없습니다"));
    if(order.getUsername().equals(loginId) || order.getStatus()!=OrderStatus.CREATE)
      throw new JobFailException("잘못된 작업입니다");
    List<OrderDto.Item> orderItems = orderItemDao.findByOrderId(orderId, "http://localhost:8080/api/images/");
    return new OrderDto.OrderDetail(order, orderItems);
  }

  @Transactional
  public void order(OrderDto.OrderRequest dto, String loginId) {
    Order order = orderDao.findById(dto.getId()).orElseThrow(()->new JobFailException("작업을 수행할 수 없습니다"));
    if(order.getUsername().equals(loginId) || order.getStatus()!=OrderStatus.CREATE)
      throw new JobFailException("잘못된 작업입니다");
    orderDao.updateAddress(dto.getId(), dto.getAddress(), dto.getZipcode());
    orderDao.updateStatus(dto.getId(), OrderStatus.PAY);
  }

  public List<OrderDto.Summary> orderList(String loginId) {
    return orderDao.findByUsername(loginId);
  }

  public OrderDto.OrderDetail orderDetails(Integer orderId, String loginId) {
    Optional<Order> order = orderDao.findById(orderId);
    if(order.isEmpty())
      throw new JobFailException("주문내역을 확인할 수 없습니다");
    List<OrderDto.Item> orderItems = orderItemDao.findByOrderId(orderId, "http://localhost:8080/api/images/");
    return new OrderDto.OrderDetail(order.get(), orderItems);
  }
}
