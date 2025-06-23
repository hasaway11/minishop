package com.example.demo.service;

import com.example.demo.dao.jpa.*;
import com.example.demo.dao.mybatis.*;
import com.example.demo.dto.*;
import com.example.demo.dto.jpa.*;
import com.example.demo.entity.account.*;
import com.example.demo.entity.product.*;
import com.example.demo.exception.*;

import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SellerProductService {
  private final ProductRepository productDao;
  private final ProductMapper productMapper;
  private final CategoryRepository categoryDao;
  private final int PAGE_SIZE = 12;

  @Transactional(readOnly=true)
  public PageResponse<ProductDto.Summary> findBySeller(int pageno, String seller) {
    List<ProductDto.Summary> products = productMapper.findAllBySeller(pageno, PAGE_SIZE, seller);
    int totalCount = productMapper.countBySeller(seller);
    return new PageResponse<>(products, pageno, PAGE_SIZE, totalCount);
  }

  public Product create(ProductDto.Create dto, String loginId) {
    Category category = categoryDao.findById(dto.getCategory()).orElseThrow(()->new EntityNotFoundException("카테고리를 찾을 수 없습니다"));
    Product product = dto.toEntity(category, new Seller(loginId));
    return productDao.save(product);
  }

  public Product read(int pno, String loginId) {
    Product product = productDao.findById(pno).orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
    if(!product.getSeller().getUsername().equals(loginId))
      throw new JobFailException("작업을 수행할 수 없습니다");
    return product;
  }

  @Transactional
  public void update(ProductDto.Update dto, String loginId) {
    Product product = productDao.findById(dto.getPno()).orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
    if(!product.getSeller().getUsername().equals(loginId))
      throw new JobFailException("작업을 수행할 수 없습니다");
    product.changeFrom(dto);
  }

  @Transactional
  public void delete(int pno, String loginId) {
    Product product = productDao.findById(pno).orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
    if(!product.getSeller().getUsername().equals(loginId))
      throw new JobFailException("작업을 수행할 수 없습니다");
    productDao.delete(product);
  }
}
