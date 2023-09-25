package com.example.demoredis.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineConfig {
	@Bean
	public Cache<String,Object> caffeineCache(){
		return Caffeine.newBuilder()
				.initialCapacity(128)//初始大小
				.maximumSize(1024)//最大数量
				.expireAfterWrite(10, TimeUnit.MINUTES)  //最多缓存10分钟
				.build();
	}
}