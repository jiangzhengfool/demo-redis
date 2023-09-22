package com.example.demoredis.config;


import com.example.demoredis.annotations.TimeToLive;
import com.example.demoredis.aspject.CacheTtlAspject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CustomizerRedisCacheManager extends RedisCacheManager {

    private final CacheTtlAspject cacheTtlAspject;

    public CustomizerRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration redisCacheConfiguration,
									   CacheTtlAspject cacheTtlAspject) {
        super(cacheWriter, redisCacheConfiguration);
        this.cacheTtlAspject = cacheTtlAspject;
    }

    @Override
    public RedisCache createRedisCache(String cacheName, RedisCacheConfiguration redisCacheConfiguration) {
        Method method = cacheTtlAspject.getCacheNameMethodCache().get(cacheName);
        if (method != null) {
            TimeToLive timeToLive = AnnotationUtils.findAnnotation(method, TimeToLive.class);
            assert timeToLive != null;
            Duration expirationTime = getExpirationTime(cacheName, timeToLive.timeUnit(), timeToLive.ttl());
            redisCacheConfiguration = redisCacheConfiguration.entryTtl(expirationTime);
        }else{
            redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(111));
        }

        return super.createRedisCache(cacheName, redisCacheConfiguration);
    }

    private Duration getExpirationTime(String cacheName, TimeUnit timeUnit, long ttl) {
        try {
            long second = timeUnit.toSeconds(ttl);
            return Duration.ofSeconds(second);
        } catch (Exception e) {
            log.error("解析指定cacheName:{},缓存失效时间异常", cacheName, e);
        }

        // 如果解析失败，直接按默认过期时间，一般是永不过期
        return Duration.ZERO;
    }
//    @Override
//    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
//        String[] array = StringUtils.delimitedListToStringArray(name, "#");
//        name = array[0];
//        if (array.length > 1) { // 解析TTL
//            // 例如 12 默认12秒， 12d=12天
//            String ttlStr = array[1];
//            Duration duration = convertDuration(ttlStr);
//            cacheConfig = cacheConfig.entryTtl(duration);
//        }
//        return super.createRedisCache(name, cacheConfig);
//    }

    private Duration convertDuration(String ttlStr) {

            return Duration.ofSeconds(Long.parseLong(ttlStr));

//
//        ttlStr = ttlStr.toUpperCase();
//
//        if (ttlStr.lastIndexOf("D") != -1) {
//            return Duration.parse("P" + ttlStr);
//        }
//
//        return Duration.parse("PT" + ttlStr);
    }




}


