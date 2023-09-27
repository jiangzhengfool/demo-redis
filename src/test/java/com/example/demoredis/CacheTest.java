package com.example.demoredis;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class CacheTest {


	private final Cache<String, Cache<String, LongAdder>> cache = Caffeine
			.newBuilder()
			.expireAfterWrite(10, TimeUnit.MINUTES)  //最多缓存10分钟
			.build();


	private Cache<String, LongAdder> getMinuteCache(String key) {

		return cache.get(key, k -> Caffeine
				.newBuilder()
				.maximumSize(1_0000)
				.build());
	}

	@Test
	void test() throws InterruptedException {
		Cache<String, LongAdder> abc = getMinuteCache("abc");
		long l = abc.get("123", (key) -> {
			return new LongAdder();
		}).longValue();
		System.out.println(l);


//		cache.get("abc",(key)->"123");
//		Thread.sleep(3000);
//		cache.get("def",(key)->"789");
//
//		System.out.println(caffeineCache.getIfPresent("abc"));
//		System.out.println(caffeineCache.getIfPresent("def"));


	}


}
