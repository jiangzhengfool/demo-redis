package com.example.demoredis;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderSyn {
	private String key;
	private LongAdder increase;

	private Long sum;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;


	LongAdderSyn(String key) {
		this.increase = new LongAdder();
		this.sum = new Long(0L);
		this.key = key;
		redisTemplate = (RedisTemplate<String, Object>) SpringUtil.getBean("redisTemplate");
		syn();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}


	public LongAdder getIncrease() {
		synchronized (this) {
			return increase;
		}

	}

	public void setIncrease(LongAdder increase) {
		this.increase = increase;
	}

	public Long getSum() {
		return sum + increase.longValue();
	}

	public void setSum(Long sum) {
		this.sum = sum;
	}


	void syn() {
		CronUtil.schedule("0/1 * * * * ?", new Task() {
			@Override
			public void execute() {
				long inc = 0L;
				synchronized (this) {
					inc = increase.longValue();
					increase = new LongAdder();
				}

				sum = redisTemplate.opsForValue().increment(key, inc);
				System.out.println("sum:" + sum);

			}
		});

		CronUtil.setMatchSecond(true);
		CronUtil.start();


	}
}
