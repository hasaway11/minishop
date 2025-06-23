package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dao.jpa.*;
import com.example.demo.dto.*;
import com.example.demo.entity.account.*;
import com.example.demo.entity.product.*;
import com.example.demo.exception.*;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SellerProductService {
  private final ProductMapper productDao;
  private final CategoryRepository categoryDao;
  private final int PAGE_SIZE = 12;

  @Transactional(readOnly=true)
  public PageResponse<ProductDto.Summary> findBySeller(int pageno, String seller) {
    List<ProductDto.Summary> products = productDao.findAllBySeller(pageno, PAGE_SIZE, seller);
    int totalCount = productDao.countBySeller(seller);
    return new PageResponse<>(products, pageno, PAGE_SIZE, totalCount);
  }

  public Product create(ProductDto.Create dto, String loginId) {
    Product product = dto.toEntity(loginId);
    return productDao.save(product);
  }

  @Transactional(readOnly=true)
  public Product read(int pno, String loginId) {
    Product product = productDao.findById(pno).orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
    product.checkSellerOrThorw(loginId);
    return product;
  }

  @Transactional
  public void update(ProductDto.Update dto, String loginId) {
    Product product = productDao.findById(dto.getProductId()).orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
    product.checkSellerOrThorw(loginId);
    productDao.update(dto);
  }

  @Transactional
  public void delete(int productId, String loginId) {
    Product product = productDao.findById(productId).orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
    product.checkSellerOrThorw(loginId);
    productDao.delete(productId);
  }
}
