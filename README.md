# demo-redis

实现 caffeineCache 作为一级缓存，Redis作为二级缓存

## 原理：

通过注解DoubleCache加AOP的形式对方法就行拦截，从而做到二级缓存。
CacheImpl implements Cache是为了之前使用caffeineCache可以无缝切换到二级缓存。

## 功能

1. 通过前缀和多实例方式实现多租户的key和ttl
2. 


