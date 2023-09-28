package com.example.demoredis;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.Scheduler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoRedisApplication.class)
@Slf4j
public class EventTest {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Test
	public void publishTest() {
		Task task = new Task();
		task.setId(1L);
		task.setTaskName("测试任务");
		task.setTaskContext("任务内容");
		task.setFinish(false);
		MyEvent event = new MyEvent(task);
		log.info("开始发布任务");
		eventPublisher.publishEvent(event);
		log.info("发布任务完成");
	}

	@Test
	void test01() throws InterruptedException {
		Cache<String, Object> cache = Caffeine.newBuilder()
				.expireAfterWrite(10, TimeUnit.SECONDS)
				.scheduler(Scheduler.forScheduledExecutorService(Executors.newScheduledThreadPool(1)))
				.removalListener((String key, Object value, RemovalCause cause) ->
						System.out.printf("Key %s was removed (%s)%n", key, cause))
				.build();
		cache.get("1929", key -> {
			return "2020";
		});
//		cache.invalidate("1929");
		Thread.sleep(300000);

	}

}
