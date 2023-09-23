package com.example.demoredis;

import com.example.demoredis.service.CacheImpl;
import com.github.benmanes.caffeine.cache.Cache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Date;

@SpringBootTest
class DemoRedisApplicationTests02 {
	@Autowired
	private Cache cache;

	@Test
	void contextLoads() {
	}
	@Test
	public void test01() {

		((CacheImpl)cache).setTtl(990);
		System.out.println(cache.getIfPresent("1234"));

		System.out.println(cache.get("1234", (k -> {
			return Collections.singletonList(""+new Date());
		})));
		System.out.println(cache.getIfPresent("1234"));
	}

}
