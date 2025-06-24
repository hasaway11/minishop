package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import com.example.demo.exception.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@RequiredArgsConstructor
@Service
public class OrderService {
  private final CartMapper cartDao;
  private final OrderMapper orderDao;
  private final OrderItemMapper orderItemDao;

  public List<CartDto.Summary> toOrderItem(List<Integer> selectedCartItemIds, String loginId) {
    return cartDao.findSelectedCartItems(selectedCartItemIds);
  }


  public void order(List<CartDto.Summary> items, String address, String loginId) {
    int orderTotalPrice = items.stream().mapToInt(CartDto.Summary::getTotalPrice).sum();
    Order order = Order.builder().username(loginId).orderDate(LocalDateTime.now()).deliveryAddress(address).status(DeliveryStatus.PAY).orderTotalPrice(orderTotalPrice).build();
    if(orderDao.save(order)==0)
      throw new JobFailException("작업을 수행할 수 없습니다");
    List<OrderItem> orderItems = new ArrayList<>();
    int orderItemId = 1;
    for(CartDto.Summary item:items) {
      OrderItem orderItem = new OrderItem(order.getOrderId(), item.getProductId(), item.getQuantity(), item.getTotalPrice());
      orderItems.add(orderItem);
    }
    orderItemDao.save(orderItems);
  }

  public List<Order> orderList(String loginId) {
    return orderDao.findByUsername(loginId);
  }

  public void orderDetails(Integer orderId, String loginId) {
  }
}
