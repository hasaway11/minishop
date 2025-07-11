package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.product.*;
import com.example.demo.exception.*;

import com.example.demo.util.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.*;

import java.awt.*;
import java.util.*;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SellerProductService {
  private final SellerProductMapper productDao;
  private final CategoryMapper categoryDao;
  private final ProductImageMapper productImageDao;

  private final int PAGE_SIZE = 12;

  @Transactional(readOnly=true)
  public PageResponse<ProductDto.SellerSummary> findBySeller(int pageno, String seller) {
    List<ProductDto.SellerSummary> products = productDao.findAllBySeller(pageno, PAGE_SIZE, seller, "http://localhost:8080/api/images/");
    int totalCount = productDao.countBySeller(seller);
    return new PageResponse<ProductDto.SellerSummary>(products, pageno, PAGE_SIZE, totalCount);
  }

  @Transactional
  public Product create(SellerProductDto.Create dto, String loginId) {
    Product product = dto.toEntity(loginId);
    List<String> imageNames = ImageUtil.savaProductImage(dto.getImages());
    productDao.save(product);
    for(String imageName:imageNames) {
      ProductImage pi = new ProductImage(null, imageName, product.getProductId());
      productImageDao.save(pi);
    }
    return product;
  }

  @Transactional(readOnly=true)
  public SellerProductDto.ProductDetail read(int productId, String loginId) {
    SellerProductDto.ProductDetail dto = productDao.findById(productId, MiniShopConstants.IMAGE_URL).orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
    if(!dto.getSeller().equals(loginId))
      throw new JobFailException("잘못된 작업입니다");
    return dto;
  }

  @Transactional
  public boolean update(SellerProductDto.Update dto, String loginId) {
    String seller = productDao.findSellerById(dto.getId()).orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
    if(!seller.equals(loginId))
      throw new JobFailException("잘못된 작업입니다");
    return productDao.update(dto)==1;
  }

  @Transactional
  public void delete(int productId, String loginId) {
    String seller = productDao.findSellerById(productId).orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
    if(!seller.equals(loginId))
      throw new JobFailException("잘못된 작업입니다");
    productDao.deleteById(productId);
    List<String> imageNames = productImageDao.findByProductId(productId);
    ImageUtil.deleteProductImages(imageNames);
    productImageDao.deleteByProductId(productId);
  }
}