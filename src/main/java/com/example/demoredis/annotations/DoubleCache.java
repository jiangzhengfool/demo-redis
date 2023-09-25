package com.example.demoredis.annotations;



import com.example.demoredis.enums.CacheType;

import java.lang.annotation.*;

/**
 * Redis过期时间注解
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DoubleCache  {

    String cacheName();
    String key(); //支持springEl表达式
    long l2TimeOut() default 12000;
    CacheType type() default CacheType.FULL;
}

