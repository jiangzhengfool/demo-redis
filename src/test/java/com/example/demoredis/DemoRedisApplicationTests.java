package com.example.demoredis;

import com.example.demoredis.pojo.Order;
import com.example.demoredis.service.CacheImpl;
import com.github.benmanes.caffeine.cache.Cache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.TreeMap;

import static com.example.demoredis.util.ElParser.parse;

@SpringBootTest
class DemoRedisApplicationTests {
	@Resource(name = "cacheImpl")
	private CacheImpl cache;

	@Test
	void contextLoads() {
	}
//	@Test
//	public void test01() {
//
//		System.out.println(cache.getIfPresent("123"));
//
//		System.out.println(cache.get("123", (k -> {
//			return Collections.singletonList("1715存在");
//		})));
//		System.out.println(cache.getIfPresent("123"));
//	}
	@Test
	public void test() {
//		String elString="#order.money";
//		String elString2="#user";
//		String elString3="#p0";
//
//		TreeMap<String,Object> map=new TreeMap<>();
//		Order order = new Order();
//		order.setId(111L);
//		order.setMoney(123D);
//		map.put("order",order);
//		map.put("user","Hydra");
//
//		String val = parse(elString, map);
//		String val2 = parse(elString2, map);
//		String val3 = parse(elString3, map);
//
//		System.out.println(val);
//		System.out.println(val2);
//		System.out.println(val3);

		System.out.println(cache.getIfPresent("3334"));
		System.out.println(cache.getIfPresent("3334"));
		System.out.println(cache.getIfPresent("3334"));
	}

}
