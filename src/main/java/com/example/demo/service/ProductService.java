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
  private final ProductMapper productMapper;
  private final int PAGE_SIZE = 12;

  @Transactional(readOnly=true)
  public PageResponse<ProductDto.Summary> findSummaries(int pageno, String seller) {
    List<ProductDto.Summary> products = productMapper.findAllBySeller(pageno, PAGE_SIZE, seller);
    int totalCount = productMapper.countBySeller(seller);
    return new PageResponse<>(products, pageno, PAGE_SIZE, totalCount);
  }

  public ProductDto.Read read(int pno) {
    Product product = productMapper.findById(pno).orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
    return product.toRead();
  }
}
