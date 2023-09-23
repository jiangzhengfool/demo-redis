package com.example.demoredis.service;

import com.example.demoredis.annotations.TimeToLive;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Policy;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Scope("prototype")
@Service
//@Scope("prototype")
@Slf4j
public class CacheImpl implements Cache<Object, Object>{
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Nullable
    @Override
    @Cacheable(value = "value#3", key = "#key")
    public Object getIfPresent(@Nonnull Object key) {
        return null;
    }

    @Nullable
    @CachePut(value = "value#3", key = "#key")
//    @TimeToLive
    @Override
    public Object get(@Nonnull Object key, @Nonnull Function<? super Object, ?> mappingFunction) {
        val apply = mappingFunction.apply(key);
        redisTemplate.keys("*");
//        redisTemplate.expire("value#3::123", 60 * 10, TimeUnit.SECONDS);
        return apply ;
    }

    @Nonnull
    @Override
    public Map<Object, Object> getAllPresent(@Nonnull Iterable<?> keys) {
        return null;
    }



    @Override
    public void put(@Nonnull Object key, @Nonnull Object value) {
        System.out.println(key);

    }

    @Override
    public void putAll(@Nonnull Map<?, ?> map) {

    }

    @Override
    public void invalidate(@Nonnull Object key) {

    }

    @Override
    public void invalidateAll(@Nonnull Iterable<?> keys) {

    }

    @Override
    public void invalidateAll() {

    }

    @Override
    public long estimatedSize() {
        return 0;
    }

    @Nonnull
    @Override
    public CacheStats stats() {
        return null;
    }

    @Nonnull
    @Override
    public ConcurrentMap<Object, Object> asMap() {
        return null;
    }

    @Override
    public void cleanUp() {

    }

    @Nonnull
    @Override
    public Policy<Object, Object> policy() {
        return null;
    }

}
