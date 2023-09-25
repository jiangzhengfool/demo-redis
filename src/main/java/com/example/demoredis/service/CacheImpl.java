package com.example.demoredis.service;


import com.example.demoredis.annotations.DoubleCache;
import com.example.demoredis.enums.CacheType;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Policy;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

//@Scope("prototype")
@Service
@Slf4j
public class CacheImpl implements  Cache{
    @DoubleCache(cacheName = "abx1",key = "#key")
    public Object getIfPresent(@Nonnull Object key) {
       return null;
    }

    @Nullable
    @DoubleCache(cacheName = "abx1", key = "#key")
    @Override
    public Object get(@NonNull Object key, @NonNull Function function) {
        return function.apply(key);
    }

    @Override
    public @NonNull Map getAll(@NonNull Iterable keys, @NonNull Function mappingFunction) {
        throw new UnsupportedOperationException("该方法尚未实现");
    }


    @Override
    @DoubleCache(cacheName = "abx1", key = "#key", type = CacheType.PUT)
    public void put(@NonNull Object key, @NonNull Object val) {

    }


//    public Object putWithReturn(@NonNull Object key, @NonNull Object val) {
//        return val;
//    }

    @Override
    public void putAll(@NonNull Map map) {

    }

    @Override
    public void invalidate(@NonNull Object o) {

    }

    @Override
    public void invalidateAll() {

    }

    @Override
    public @NonNegative long estimatedSize() {
        return 0;
    }

    @Override
    public @NonNull CacheStats stats() {
        return null;
    }

    @Override
    public @NonNull ConcurrentMap asMap() {
        return null;
    }

    @Override
    public void cleanUp() {

    }

    @Override
    public @NonNull Policy policy() {
        return null;
    }

    @Override
    public void invalidateAll(@NonNull Iterable iterable) {

    }

    @Override
    public @NonNull Map getAllPresent(@NonNull Iterable iterable) {
        return null;
    }


}
