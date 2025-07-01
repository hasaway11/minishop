package com.example.demo.service;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CategoryService {
  private final CategoryMapper categoryDao;

  public List<CategoryDto> readAll() {
    return categoryDao.findAll();
  }

  public List<CategoryDto> readMinorCategory() {
    return categoryDao.findByMinorCategory();
  }
}
