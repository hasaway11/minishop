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
  public PageResponse<ProductDto.Summary> findSummaries(int pageno) {
    List<ProductDto.Summary> products = productDao.findAll(pageno, PAGE_SIZE);
    int totalCount = productDao.count();
    return new PageResponse<>(products, pageno, PAGE_SIZE, totalCount);
  }

  public ProductDto.Read read(int productId) {
    Product product = productDao.findById(productId).orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
    List<String> imageNames = productImageDao.findByProductId(productId);
    return product.toRead(imageNames);
  }
}
