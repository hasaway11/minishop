package com.example.demo.service;


import com.example.demo.dao.*;
import com.example.demo.dto.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SellerOrderService {
  private final SellerOrderMapper sellerOrderDao;

  public List<SellerOrderDto.OrderDetail> findBySeller(String loginId) {
    return sellerOrderDao.findBySeller(loginId, "http://localhost:8080/api/images/");
  }

  public List<SellerOrderDto.OrderDetail> updateStatus(List<SellerOrderDto.OrderItemId> list) {
    return null;
  }
}
