package com.example.demo;

import com.example.demo.dao.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MinishopApplicationTests {
	@Autowired
	private AccountRepository accountDao;

	@Test
	void contextLoads() {
		System.out.println(accountDao);
	}

}
