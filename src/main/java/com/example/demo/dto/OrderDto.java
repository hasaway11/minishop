package com.example.demo.dto;

import com.example.demo.entity.order.*;
import lombok.*;

import java.time.*;
import java.util.*;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class OrderDto {
  @Data
  public static class OrderList {
    List<Integer> list;
  }

  @Data
  public static class Item {
    private Integer productId;
    private String name;
    private String image;
    private Integer quantity;
    private Integer totalPrice;
  }

  @Data
  public static class Orders {
    private Integer id;
    private LocalDateTime displayTime;
    private OrderStatus status;
    private Integer orderTotalPrice;
    private List<OrderDto.Item> orderItems;

    public Orders(Order order, List<OrderDto.Item> orderItems) {
      this.id = order.getOrderId();
      this.status = order.getStatus();
      this.orderTotalPrice = order.getOrderTotalPrice();
      this.orderItems = orderItems;

      if(this.status==OrderStatus.CREATE)
        this.displayTime = order.getOrderAt();
      else if(this.status==OrderStatus.CANCEL)
        this.displayTime = order.getCancelAt();
      else
        this.displayTime = order.getPaidAt();
    }
  }
}
