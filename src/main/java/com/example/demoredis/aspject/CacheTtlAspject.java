package com.example.demoredis.aspject;


import com.example.demoredis.annotations.TimeToLive;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Spring cache 过期配置AOP切面
 **/
@Aspect
@Component
public class CacheTtlAspject implements Ordered {

    private HashMap<String, Method> cacheNameMethodCache = new HashMap<>(16);


    @Before("@annotation(com.example.demoredis.annotations.TimeToLive)")
    public void before(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        TimeToLive timeToLive = AnnotationUtils.findAnnotation(method, TimeToLive.class);
        Cacheable cacheable = AnnotationUtils.findAnnotation(method, Cacheable.class);
        CachePut cachePut = AnnotationUtils.findAnnotation(method, CachePut.class);

        if (cacheable != null) {
            writeMap(cacheable.cacheNames(), method, timeToLive);
        }
        if (cachePut != null) {
            // 方法可能是加了Cacheable或者CachePut注解，所以都要放进去缓存起来
            writeMap(cachePut.cacheNames(), method, timeToLive);
        }
    }

    @Override
    public int getOrder() {
        // 接口并实现getOrder方法，因为@Cacheable这些也是通过AOP来实现的，会涉及到优先级问题，getOrder值越小越先执行，
        // 这里必须让CacheTtlAspject先于@Cacheable执行
        return 10001;  // 注意这个值，如果跟其它切面定义相同会导致 此 切面类 失效，踩过坑。。。
    }

    private void writeMap(String[] cacheNames, Method method, TimeToLive timeToLive) {
        if(timeToLive != null){
            for(String cacheName : cacheNames){
                if(!cacheNameMethodCache.containsKey(cacheName)){
                    cacheNameMethodCache.put(cacheName, method);
                }
            }
        }
    }

    public HashMap<String, Method> getCacheNameMethodCache() {
        return cacheNameMethodCache;
    }
}


