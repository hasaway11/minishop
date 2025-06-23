package com.example.demo.dao.jpa;

import com.example.demo.entity.product.*;
import org.springframework.data.jpa.repository.*;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
