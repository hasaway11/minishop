package com.example.demo.dao;

import com.example.demo.dto.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface CategoryMapper {
  List<CategoryDto> findAll();
}
