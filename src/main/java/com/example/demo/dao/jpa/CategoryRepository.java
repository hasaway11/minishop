package com.example.demo.dao.jpa;

import com.example.demo.entity.product.*;
import org.springframework.data.jpa.repository.*;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
