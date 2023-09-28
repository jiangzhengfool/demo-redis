package com.example.demoredis;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.atomic.LongAdder;

public class LongAdderSyn {
	private String key;
	private LongAdder increase;

	private long sum;

	private RedisTemplate<String, Object> redisTemplate;


	LongAdderSyn(String key) {
		this.increase = new LongAdder();
		this.sum = new Long(0L);
		this.key = key;
		redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
		syn();
	}


	public void increment() {
		synchronized (this) {
			increase.increment();
		}

	}

	public long longValue() {
		return sum + increase.longValue();
	}


	void syn() {
		redisTemplate.opsForValue().setIfAbsent(key, 0);
		CronUtil.schedule("0/1 * * * * ?", new Task() {
			@Override
			public void execute() {
				long inc = 0L;
				synchronized (this) {
					inc = increase.longValue();
//					try {
//						Thread.sleep(10000);
//					} catch (InterruptedException e) {
//						throw new RuntimeException(e);
//					}
					increase.reset();
				}
				System.out.println("sum:" + sum);
				sum = redisTemplate.opsForValue().increment(key, inc);


			}
		});

		CronUtil.setMatchSecond(true);
		CronUtil.start();


	}
}
