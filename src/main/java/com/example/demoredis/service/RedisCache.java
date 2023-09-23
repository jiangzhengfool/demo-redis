package com.example.demoredis.service;

import com.github.benmanes.caffeine.cache.Cache;

public interface RedisCache extends Cache<Object, Object> {
    public int ttl = 0;

}
