package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.product.*;
import com.example.demo.exception.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ProductService {
  private final ProductMapper productDao;
  private final ProductImageMapper productImageDao;
  private final int PAGE_SIZE = 12;

  @Transactional(readOnly=true)
  public PageResponse<ProductDto.Summary> findSummaries(int pageno, String seller) {
    List<ProductDto.Summary> products = null;
    int totalCount = 0;
    if("".equals(seller)) {
      products = productDao.findAll(pageno, PAGE_SIZE);
      totalCount = productDao.count(null);
    } else {
      products = productDao.findAllBySeller(pageno, PAGE_SIZE, seller);
      totalCount = productDao.count(seller);
    }
    return new PageResponse<>(products, pageno, PAGE_SIZE, totalCount);
  }

  public ProductDto.Read read(int id) {
    return productDao.findById(id, "/api/images/").orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
  }
}
