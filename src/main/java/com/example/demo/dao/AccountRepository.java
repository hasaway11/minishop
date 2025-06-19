package com.example.demo.dao;

import com.example.demo.entity.account.*;
import org.springframework.data.jpa.repository.*;

public interface AccountRepository extends JpaRepository<Account, String> {
}
