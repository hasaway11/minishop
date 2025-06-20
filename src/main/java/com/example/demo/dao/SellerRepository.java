package com.example.demo.dao;

import com.example.demo.entity.account.*;
import org.springframework.data.jpa.repository.*;

public interface SellerRepository extends JpaRepository<Seller, String> {
}
