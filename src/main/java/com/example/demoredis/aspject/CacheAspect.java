package com.example.demoredis.aspject;


import com.example.demoredis.annotations.DoubleCache;
import com.example.demoredis.enums.CacheType;
import com.example.demoredis.util.ElParser;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * Spring cache 过期配置AOP切面
 **/
@Slf4j
@Component
@Aspect
//@AllArgsConstructor
public class CacheAspect  {

    @Resource(name = "caffeineCache")
    private Cache cache;

    private  RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    void init() {
//        // 否则会使用默认的序列化，导致key出现一些乱码前缀
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
////        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        GenericObjectPoolConfig redisPool = new GenericObjectPoolConfig();

        Map<String, Object> source = new HashMap<>(8);
        source.put("spring.redis.cluster.nodes", "100.122.160.132:6379,100.122.160.132:6380,100.122.160.132:6381");
        RedisClusterConfiguration secondaryRedisClusterConfig;
        secondaryRedisClusterConfig = new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
//		redisClusterConfiguration.setPassword(environment.getProperty("spring.redis.password"));

        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(redisPool).build();


        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(secondaryRedisClusterConfig, clientConfiguration);
        lettuceConnectionFactory.afterPropertiesSet();


        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);


        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);

        template.afterPropertiesSet();
        redisTemplate = template;
    }


    @Pointcut("@annotation(com.example.demoredis.annotations.DoubleCache)")
    public void cacheAspect() {
    }

    @Around("cacheAspect()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        //拼接解析springEl表达式的map
        String[] paramNames = signature.getParameterNames();
        Object[] args = point.getArgs();

        TreeMap<String, Object> treeMap = new TreeMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            treeMap.put(paramNames[i], args[i]);
        }


        // 获取超时时间

        Field fTTL = point.getTarget().getClass().getDeclaredField("ttl");
        fTTL.setAccessible(true);
        int ttl = (int) fTTL.get(point.getTarget());


        // prefix 前缀
        Field f = point.getTarget().getClass().getDeclaredField("prefix");
        f.setAccessible(true);
        String prefix = (String) f.get(point.getTarget());

        DoubleCache annotation = method.getAnnotation(DoubleCache.class);
        String elResult = (String) ElParser.parse(annotation.key(), treeMap);
        String realKey = annotation.cacheName() + prefix + elResult;


        //强制更新
        if (annotation.type() == CacheType.PUT) {
//            Object object = point.proceed();
            Object object = ElParser.parse("#val", treeMap);
            redisTemplate.opsForValue().set(realKey, object, ttl, TimeUnit.SECONDS);
            cache.put(realKey, object);
            return object;
        }
        //删除
        else if (annotation.type() == CacheType.DELETE) {
            redisTemplate.delete(realKey);
            cache.invalidate(realKey);
            return point.proceed();
        }



        //读写，查询Caffeine
        Object caffeineCache = cache.getIfPresent(realKey);
        if (Objects.nonNull(caffeineCache)) {
            log.info("get data from caffeine");
            return caffeineCache;
        }

        //查询Redis
        Object redisCache = redisTemplate.opsForValue().get(realKey);
        if (Objects.nonNull(redisCache)) {
            log.info("get data from redis");
            cache.put(realKey, redisCache);
            return redisCache;
        }

        log.info("get data from database");
        Object object = point.proceed();
        if (Objects.nonNull(object)){
            //写入Redis
            redisTemplate.opsForValue().set(realKey, object, ttl, TimeUnit.SECONDS);
            //写入Caffeine
            cache.put(realKey, object);
        }
        return object;
    }
}


