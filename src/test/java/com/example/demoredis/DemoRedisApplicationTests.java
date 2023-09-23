package com.example.demoredis;

import com.github.benmanes.caffeine.cache.Cache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sun.util.calendar.BaseCalendar;

import java.util.Collections;
import java.util.Date;

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
			return Collections.singletonList(""+new Date());
		})));
		System.out.println(cache.getIfPresent("123"));
	}

}
