package com.example.demoredis;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class CacheTest {
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@BeforeEach
	void test01() {

		redisTemplate.setKeySerializer(new StringRedisSerializer());


	}

	Cache<String, LongAdderSyn> caffeineCache = Caffeine.newBuilder()
			.initialCapacity(128)//初始大小
			.maximumSize(1024)//最大数量
			.expireAfterWrite(10, TimeUnit.MINUTES)  //最多缓存10分钟
			.build();

	@Test
	void test() throws InterruptedException {
		LongAdderSyn obj = caffeineCache.get("key", (key) -> {
			return new LongAdderSyn((String) key);
		});

		while (true) {
			obj.getIncrease().increment();
			System.out.println("inc:" + obj.getIncrease().longValue());
			Thread.sleep(1000);
		}
//		obj.getIncrease().increment();
//
//		obj = caffeineCache.getIfPresent("key");
//		System.out.println(obj.getSum());
//		LongAdderSyn abc = new LongAdderSyn("abc");
//		abc.setRedisTemplate(redisTemplate);

//		CronUtil.schedule("0/1 * * * * ?", new Task() {
//			@Override
//			public void execute() {
//
//				System.out.println("sum:");
//
//			}
//		});
//
//		CronUtil.start();
//		redisTemplate.opsForValue().increment("key", 90);


//		Thread.sleep(100000);


	}
}
