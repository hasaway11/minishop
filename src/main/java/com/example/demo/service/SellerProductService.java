package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.product.*;
import com.example.demo.exception.*;

import com.example.demo.util.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SellerProductService {
  private final ProductMapper productDao;
  private final CategoryMapper categoryDao;
  private final ProductImageMapper productImageDao;

  private final int PAGE_SIZE = 12;

  @Transactional(readOnly=true)
  public PageResponse<ProductDto.Summary> findBySeller(int pageno, String seller) {
    List<ProductDto.Summary> products = productDao.findAllBySeller(pageno, PAGE_SIZE, seller);
    int totalCount = productDao.countBySeller(seller);
    return new PageResponse<>(products, pageno, PAGE_SIZE, totalCount);
  }

  @Transactional
  public Product create(ProductDto.Create dto, String loginId) {
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
  public ProductDto.Read read(int productId, String loginId) {
    Product product = productDao.findById(productId).orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
    List<String> imageNames = productImageDao.findByProductId(productId);
    product.checkSellerOrThorw(loginId);
    return product.toRead(imageNames);
  }

  @Transactional
  public void update(ProductDto.Update dto, String loginId) {
    Product product = productDao.findById(dto.getProductId()).orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
    product.checkSellerOrThorw(loginId);
    // 가격 변경은 기준 시점을 정확히 정해두는 것이 바람직
    // 가격 정보는 redis에 캐시. 가격 정보를 변경하면 정해진 시간에 cache를 invalidate
    productDao.update(dto);
  }

  @Transactional
  public void delete(int productId, String loginId) {
    Product product = productDao.findById(productId).orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
    product.checkSellerOrThorw(loginId);
    productDao.delete(productId);
    List<String> imageNames = productImageDao.findByProductId(productId);
    ImageUtil.deleteProductImages(imageNames);
    productImageDao.deleteByProductId(productId);
  }
}
