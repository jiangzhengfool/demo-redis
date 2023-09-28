package com.example.demoredis;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.atomic.LongAdder;

@Slf4j
public class LongAdderSyn {
	private String prefix;
	private String key;
	private LongAdder increase;

	private long sum;

	private RedisTemplate<String, Object> redisTemplate;

	LongAdderSyn(String prefix, String key) {
		this(prefix + key);
	}

	LongAdderSyn(String key) {
		this.increase = new LongAdder();
		this.sum = new Long(0L);
		this.key = key;
		redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");

		syn1();
	}


	public void increment() {
//		synchronized (this) {
			increase.increment();
//		}

	}

	public long longValue() {
		return sum + increase.longValue();
	}


	void syn() {
		redisTemplate.opsForValue().setIfAbsent(key, 0);
		CronUtil.schedule(key, "0/1 * * * * ?", new Task() {
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

	void syn1() {
		log.info("init start");

		redisTemplate.opsForValue().setIfAbsent(key, 0);
		log.info("init success");
		CronUtil.schedule(key, "0/1 * * * * ?", new Task() {
			@Override
			public void execute() {
				long inc = 0L;
				inc = increase.longValue();
				if (inc == 0) {
					// 此时没必同步给redis
					return;
				}
				sum = redisTemplate.opsForValue().increment(key, inc);
				log.info("sum:" + sum);
				increase.add(-inc);


			}
		});

		CronUtil.setMatchSecond(true);
		CronUtil.start();


	}
}
