package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entity.product.*;
import com.example.demo.exception.*;
import com.example.demo.util.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ProductService {
  private final ProductMapper productDao;
  private final ProductImageMapper productImageDao;
  private final int PAGE_SIZE = 12;

  @Transactional(readOnly=true)
  public PageResponse<ProductDto.Summary> findAll(int pageno, String seller) {
    List<ProductDto.Summary> products = productDao.findAll(pageno, PAGE_SIZE,  seller, "http://localhost:8080/api/images/");
    int totalCount = productDao.count(null);
    return new PageResponse<>(products, pageno, PAGE_SIZE, totalCount);
  }

  @Transactional(readOnly=true)
  public ProductDto.Read read(int id) {
    ProductDto.Read dto =  productDao.findById(id, "http://localhost:8080/api/images/").orElseThrow(()->new EntityNotFoundException("상품을 찾을 수 없습니다"));
    return dto.setImages(productImageDao.findImageUrlsByProductId(id, MiniShopConstants.IMAGE_URL));
  }
}
