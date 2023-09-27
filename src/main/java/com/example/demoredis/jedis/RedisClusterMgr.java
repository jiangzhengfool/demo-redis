//package com.example.demoredis.jedis;
//
//
//import lombok.extern.slf4j.Slf4j;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.exceptions.JedisNoScriptException;
//
//import java.util.Arrays;
//import java.util.Properties;
//import java.util.Set;
//
//
//@Slf4j
//public class RedisClusterMgr extends RedisBase implements IRedis {
//
//    private static volatile RedisClusterMgr instance;
//
//    public static volatile String REDIS_LUA_HSET_EX_HASH;
//
//    public static volatile String REDIS_LUA_HINCR_EX_HASH;
//
//    public static RedisClusterMgr getInstance() {
//        if (instance == null) {
//            synchronized (RedisClusterMgr.class) {
//                if (instance == null) {
//                    instance = new RedisClusterMgr();
//                }
//            }
//        }
//        return instance;
//    }
//
//
//    private static volatile JedisCluster jedisCluster;
//
//
//    /**
//     * 集群
//     */
//    public static JedisCluster getJedisCluster() {
//        if (jedisCluster == null) {
//            synchronized (JedisTool.class) {
//                if (jedisCluster == null) {
//                    Properties properties = INacosConfigClient.getConfigProperties();
//                    String pwd = getProperties(properties, REDIS_PASSWORD_KEY, REDIS_PASSWORD_CLUSTER);
//                    String redisHost = getProperties(properties, REDIS_HOST_CLUSTER);
//                    jedisCluster = new RedisCluster().getJedisCluster(redisHost, pwd);
////                    REDIS_LUA_HSET_EX_HASH = RedisClusterMgr.getInstance().load(SCRIPT_HSET_EX);
////                    REDIS_LUA_HINCR_EX_HASH = RedisClusterMgr.getInstance().load(SCRIPT_HINCR_EX);
//                    log.info("redis lua hset ex hash = {}, hincr ex hash={}", REDIS_LUA_HSET_EX_HASH, REDIS_LUA_HINCR_EX_HASH);
//
//                }
//            }
//        }
//        return jedisCluster;
//    }
//
//    @Override
//    public String set(String key, String value) {
//        JedisCluster jedisCluster = null;
//        try {
//            jedisCluster = getJedisCluster();
//            return jedisCluster.set(key, value);
//        } catch (Exception e) {
//            log.error("set redis error", e);
//        } finally {
//            close(jedisCluster);
//
//        }
//        return null;
//
//    }
//
//    @Override
//    public String setEx(String key, String value, int expireTime) {
//        JedisCluster jedisCluster = null;
//        try {
//            jedisCluster = getJedisCluster();
//            return jedisCluster.setex(key, expireTime, value);
//        } catch (Exception e) {
//            log.error("setEx  error", e);
//        } finally {
//            close(jedisCluster);
//
//        }
//        return null;
//    }
//
//    @Override
//    public Long hset(String key, String field, String value) {
//        JedisCluster jedisCluster = null;
//        try {
//            jedisCluster = getJedisCluster();
//            return jedisCluster.hset(key, field, value);
//        } catch (Exception e) {
//            log.error("hset redis error", e);
//        } finally {
//            close(jedisCluster);
//
//        }
//        return null;
//    }
//
//    @Override
//    public Long hsetnx(String key, String field, String value) {
//        JedisCluster jedisCluster = null;
//        try {
//            jedisCluster = getJedisCluster();
//            return jedisCluster.hsetnx(key, field, value);
//        } catch (Exception e) {
//            log.error("hsetnx redis error", e);
//        } finally {
//            close(jedisCluster);
//
//        }
//        return null;
//    }
//
//    @Override
//    public Object hsetEx(String key, String field, String value, Long expire) {
//        JedisCluster jedisCluster = null;
//        String expireTime = null;
//        try {
//            expireTime = String.valueOf(expire);
//            jedisCluster = getJedisCluster();
//            return jedisCluster.evalsha(REDIS_LUA_HSET_EX_HASH, Arrays.asList(key), Arrays.asList(field, value, expireTime));
//        } catch (JedisNoScriptException e) {
//            log.warn("key={}对应的master未Load script, load first.", key);
//            Boolean scriptExist = jedisCluster.scriptExists(REDIS_LUA_HSET_EX_HASH, key);
//            if (!scriptExist) {
//                String hash = jedisCluster.scriptLoad(SCRIPT_HSET_EX, key);
//                if (hash.equals(REDIS_LUA_HSET_EX_HASH)) {
//                    log.info("lua hash is equal, hash={}", hash);
//                } else {
//                    log.warn("lua hash is not equal,REDIS_LUA_HSET_EX_HASH={}, hash={}", REDIS_LUA_HSET_EX_HASH, hash);
//                }
//            }
//            return jedisCluster.evalsha(REDIS_LUA_HSET_EX_HASH, Arrays.asList(key), Arrays.asList(field, value, expireTime));
//
//        } catch (Exception e) {
//            log.error("hsetEx redis error", e);
//        } finally {
//            close(jedisCluster);
//
//        }
//        return null;
//    }
//
//    @Override
//    public String hget(String key, String field) {
//        JedisCluster jedisCluster = null;
//        try {
//            jedisCluster = getJedisCluster();
//            return jedisCluster.hget(key, field);
//        } catch (Exception e) {
//            log.error("hget redis error", e);
//        } finally {
//            close(jedisCluster);
//
//        }
//        return null;
//    }
//
//    @Override
//    public Long del(String... keys) {
//        JedisCluster jedisCluster = null;
//        try {
//            jedisCluster = getJedisCluster();
//            return jedisCluster.del(keys);
//        } catch (Exception e) {
//            log.error("redis del error", e);
//        } finally {
//            close(jedisCluster);
//
//        }
//        return null;
//    }
//
//    @Override
//    public Long hincrBy(String key, String field, Long value) {
//        JedisCluster jedisCluster = null;
//        try {
//            jedisCluster = getJedisCluster();
//            return jedisCluster.hincrBy(key, field, value);
//        } catch (Exception e) {
//            log.error("redis del error", e);
//        } finally {
//            close(jedisCluster);
//
//        }
//        return null;
//    }
//
//    @Override
//    public Object hincrByEx(String key, String field, Long value, Long expire) {
//        JedisCluster jedisCluster = null;
//        String expireTime = null;
//        try {
//            expireTime = String.valueOf(expire);
//            jedisCluster = getJedisCluster();
//            return jedisCluster.evalsha(REDIS_LUA_HINCR_EX_HASH,
//                    Arrays.asList(key), Arrays.asList(field, String.valueOf(value), expireTime));
//        } catch (JedisNoScriptException e) {
//            log.warn("key={}对应的master未Load script, load first.", key);
//            Boolean scriptExist = jedisCluster.scriptExists(REDIS_LUA_HINCR_EX_HASH, key);
//            if (!scriptExist) {
//                String hash = jedisCluster.scriptLoad(SCRIPT_HINCR_EX, key);
//                if (hash.equals(REDIS_LUA_HINCR_EX_HASH)) {
//                    log.info("lua hash is equal, hash={}", hash);
//                } else {
//                    log.warn("lua hash is not equal,REDIS_LUA_HINCR_EX_HASH={}, hash={}", REDIS_LUA_HINCR_EX_HASH, hash);
//                }
//            }
//            return jedisCluster.evalsha(REDIS_LUA_HINCR_EX_HASH,
//                    Arrays.asList(key), Arrays.asList(field, String.valueOf(value), expireTime));
//
//        } catch (Exception e) {
//            log.error("hincrByEx redis error", e);
//        } finally {
//            close(jedisCluster);
//
//        }
//        return null;
//    }
//
//    @Override
//    public Set<String> hkeys(String key) {
//        JedisCluster jedisCluster = null;
//        try {
//            jedisCluster = getJedisCluster();
//            return jedisCluster.hkeys(key);
//        } catch (Exception e) {
//            log.error("redis del error", e);
//        } finally {
//            close(jedisCluster);
//
//        }
//        return null;
//    }
//
//    @Override
//    public String load(String script) {
//
//        JedisCluster jedisCluster = null;
//        try {
//            jedisCluster = getJedisCluster();
//            return jedisCluster.scriptLoad(script, "nck");
//        } catch (Exception e) {
//            log.error("redis del error", e);
//        } finally {
//            close(jedisCluster);
//
//        }
//        return null;
//    }
//
//    @Override
//    public Long zadd(String key, String value, Double score, Integer expire) {
//        JedisCluster jedisCluster = null;
//        try {
//            jedisCluster = getJedisCluster();
//            Long zadd = jedisCluster.zadd(key, score, value);
//            jedisCluster.expire(key, expire);
//            return zadd;
//        } catch (Exception e) {
//            log.error("redis zadd error", e);
//        } finally {
//            close(jedisCluster);
//
//        }
//        return null;
//    }
//
//    @Override
//    public Long zcount(String key, Double min, Double max) {
//        JedisCluster jedisCluster = null;
//        try {
//            jedisCluster = getJedisCluster();
//            return jedisCluster.zcount(key, min, max);
//        } catch (Exception e) {
//            log.error("redis zcount error", e);
//        } finally {
//            close(jedisCluster);
//
//        }
//        return 0L;
//    }
//
//    @Override
//    public Long zremrangeByScore(String key, Double min, Double max) {
//        JedisCluster jedisCluster = null;
//        try {
//            jedisCluster = getJedisCluster();
//            return jedisCluster.zremrangeByScore(key, min, max);
//        } catch (Exception e) {
//            log.error("redis zremrangeByScore error", e);
//        } finally {
//            close(jedisCluster);
//
//        }
//        return 0L;
//    }
//
//
//}
