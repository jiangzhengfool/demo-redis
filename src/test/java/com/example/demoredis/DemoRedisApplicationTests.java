package com.example.demoredis;

import com.github.benmanes.caffeine.cache.Cache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
class DemoRedisApplicationTests {
	@Autowired
	private Cache cache;

	@Test
	void contextLoads() {
	}
	@Test
	public void test01() {

		System.out.println(cache.getIfPresent("123"));

		System.out.println(cache.get("123", (k -> {
			return Collections.singletonList("1715存在");
		})));
		System.out.println(cache.getIfPresent("123"));
	}

}
