package com.example.demoredis.annotations;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis过期时间注解
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeToLive {

    long ttl() default 60 * 1000;

    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

}

