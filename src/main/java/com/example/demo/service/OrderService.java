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
  private final OrderMapper orderDao;
  private final OrderItemMapper orderItemDao;

  public int prepareOrder(List<Integer> selectedCartItemIds, String loginId) {
    List<CartDto.Summary> cartSummaries = cartDao.findSelectedCartItems(selectedCartItemIds, loginId);
    int orderTotalPrice = cartSummaries.stream().mapToInt(CartDto.Summary::getTotalPrice).sum();
    Order order = Order.builder().username(loginId).orderAt(LocalDateTime.now()).status(OrderStatus.CREATE).orderTotalPrice(orderTotalPrice).build();
    orderDao.save(order);
    List<OrderItem> orderItems = new ArrayList<>();
    for(CartDto.Summary item:cartSummaries) {
      OrderItem orderItem = new OrderItem(order.getOrderId(), item.getId(), item.getQuantity(), item.getTotalPrice(), true);
      orderItems.add(orderItem);
    }
    orderItemDao.save(orderItems);
    return order.getOrderId();
  }

  public OrderDto.Orders read(Integer id, String loginId) {
    Optional<Order> order =  orderDao.findByIdAndUsername(id, loginId);
    if(order.isEmpty())
      throw new JobFailException("유효하지 않은 주문입니다");
    List<OrderDto.Item> orderItems = orderItemDao.findById(id);
    return new OrderDto.Orders(order.get(), orderItems);
  }

  @Transactional
  public void order(Integer id, String address, String loginId) {
    Optional<Order> order =  orderDao.findByIdAndUsername(id, loginId);
    if(order.isEmpty())
      throw new JobFailException("유효하지 않은 주문입니다");
    orderDao.updateAddress(id, address);
    orderDao.updateStatus(id, OrderStatus.PAY);
  }

  public List<Order> orderList(String loginId) {
    return orderDao.findByUsername(loginId);
  }

  public OrderDto.Orders orderDetails(Integer orderId, String loginId) {
    Optional<Order> order = orderDao.findByIdAndUsername(orderId, loginId);
    if(order.isEmpty())
      throw new JobFailException("주문내역을 확인할 수 없습니다");
    List<OrderDto.Item> orderItems = orderItemDao.findById(orderId);
    return new OrderDto.Orders(order.get(), orderItems);
  }
}
