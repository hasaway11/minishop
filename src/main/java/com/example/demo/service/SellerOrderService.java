package com.example.demo.service;


import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.order.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SellerOrderService {
  private final SellerOrderMapper sellerOrderDao;

  public List<SellerOrderDto.OrderSummary > findBySeller(String loginId) {
    return sellerOrderDao.findBySeller(loginId);
  }

  public List<SellerOrderDto.OrderSummary > updateStatus(List<Integer> orderItemIds, String loginId) {
    sellerOrderDao.updateStatus(orderItemIds, OrderStatus.SHIPPING);
    return sellerOrderDao.findBySeller(loginId);
  }
}
