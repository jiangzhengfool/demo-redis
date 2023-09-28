package com.example.demoredis;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
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
		GenericToStringSerializer genericToStringSerializer = new GenericToStringSerializer(Object.class);
		// 6.序列化类，对象映射设置
		// 7.设置 value 的转化格式和 key 的转化格式
		redisTemplate.setValueSerializer(genericToStringSerializer);


	}

	Cache<String, LongAdderSyn> caffeineCache = Caffeine.newBuilder()
			.initialCapacity(128)//初始大小
			.maximumSize(1024)//最大数量
			.expireAfterWrite(10, TimeUnit.MINUTES)  //最多缓存10分钟
			.build();

	@Test
	void test() throws InterruptedException {
		redisTemplate.opsForValue().set("20230945", 0);
//		CountDownLatch countDownLatch = new CountDownLatch(100);
		LongAdderSyn obj = caffeineCache.get("20230945", (key) -> {
			return new LongAdderSyn((String) key);
		});

		for (int i = 0; i < 10000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {

					obj.increment();
//					countDownLatch.countDown();
				}
			}).start();
		}
//		countDownLatch.await();

		while (true) {

		}
//		System.out.println(obj.longValue());


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

	@Test
	void test1() throws InterruptedException {
//		redisTemplate.opsForValue().set("20231023", 0);
//		CountDownLatch countDownLatch = new CountDownLatch(100);


		for (int i = 0; i < 10000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					LongAdderSyn obj = caffeineCache.get("20231023", (key) -> {
						return new LongAdderSyn((String) key);
					});

					obj.increment();
//					countDownLatch.countDown();
				}
			}).start();
		}
//		countDownLatch.await();

		while (true) {

		}


	}
}
