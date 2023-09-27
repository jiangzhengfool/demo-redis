//package com.example.demoredis.jedis;
//
//import java.util.Set;
//
///**
// * @author llf
// * @program: svc_epc_plugin_risk_rule
// * @desc
// * @date 2022/11/7 19:09
// */
//public interface IRedis {
//
//    String set(String key, String value);
//
//    String setEx(String key, String value, int expireTime);
//
//    Long hset(String key, String field, String value);
//
//    Long hsetnx(String key, String field, String value);
//
//    Object hsetEx(String key, String field, String value, Long expire);
//
//    String hget(String key, String field);
//
//
//    Long del(String... key);
//
//    Long hincrBy(String key, String field, Long value);
//
//    Object hincrByEx(String key, String field, Long value, Long expire);
//
//    Set<String> hkeys(String key);
//
//    String load(String script);
//
//    Long zadd(String key, String value, Double score, Integer expire);
//
//    Long zcount(String key, Double min, Double max);
//
//    Long zremrangeByScore(String key, Double min, Double max);
//}
