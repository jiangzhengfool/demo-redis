package com.example.demoredis.service;

import com.example.demoredis.annotations.TimeToLive;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Policy;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
    @Nullable
    @Override
    @Cacheable(value = "value#3", key = "#key")
    public Object getIfPresent(@Nonnull Object key) {
        return null;
    }

    @Nullable
    @CachePut(value = "value#3", key = "#key")
    @TimeToLive
    @Override
    public Object get(@Nonnull Object key, @Nonnull Function<? super Object, ?> mappingFunction) {

        return  mappingFunction.apply(key);
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
