package com.example.demo.dao;

import com.example.demo.entity.order.*;
import org.springframework.data.jpa.repository.*;

public interface OrderRepository  extends JpaRepository<Order, Long> {
}

