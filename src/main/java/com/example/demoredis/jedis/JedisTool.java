//package com.example.demoredis.jedis;
//
//import com.hzmc.epc.plugin.asset.account.common.mybatis.ClusterModeEnum;
//import com.hzmc.epc.utils.INacosConfigClient;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.Properties;
//import java.util.Set;
//
//
///**
// * @author llf
// * @program: svc_epc_plugin_risk_rule
// * @desc
// * @date 2022/11/7 14:17
// */
//@Slf4j
//public class JedisTool implements IRedis {
//
//    private static volatile JedisTool instance;
//
//    public boolean isCluster() {
//        return isCluster;
//    }
//
//    private boolean isCluster = false;
//
//    private JedisTool() {
//        Properties properties = INacosConfigClient.getConfigProperties();
//        isCluster = ClusterModeEnum.CLUSTER.getType().equals(PropUtil.getProperties(properties, "is_cluster", ClusterModeEnum.ALONE.getType()));
//
//    }
//
//    public static JedisTool getInstance() {
//        if (instance == null) {
//            synchronized (JedisTool.class) {
//                if (instance == null) {
//                    instance = new JedisTool();
//                }
//            }
//        }
//        return instance;
//    }
//
//    @Override
//    public String set(String key, String value) {
//        return isCluster ? RedisClusterMgr.getInstance().set(key, value)
//                : RedisAloneMgr.getInstance().set(key, value);
//    }
//
//    @Override
//    public String setEx(String key, String value, int expireTime) {
//        return isCluster ? RedisClusterMgr.getInstance().setEx(key, value, expireTime)
//                : RedisAloneMgr.getInstance().setEx(key, value, expireTime);
//    }
//
//    @Override
//    public Long hset(String key, String field, String value) {
//        return isCluster ? RedisClusterMgr.getInstance().hset(key, field, value)
//                : RedisAloneMgr.getInstance().hset(key, field, value);
//    }
//
//    @Override
//    public Long hsetnx(String key, String field, String value) {
//        return isCluster ? RedisClusterMgr.getInstance().hsetnx(key, field, value)
//                : RedisAloneMgr.getInstance().hsetnx(key, field, value);
//    }
//
//    @Override
//    public Object hsetEx(String key, String field, String value, Long expire) {
//        return isCluster ? RedisClusterMgr.getInstance().hsetEx(key, field, value, expire)
//                : RedisAloneMgr.getInstance().hsetEx(key, field, value, expire);
//    }
//
//
//    @Override
//    public String hget(String key, String field) {
//        if (StringUtils.isNotBlank(key)) {
//            return isCluster ? RedisClusterMgr.getInstance().hget(key, field)
//                    : RedisAloneMgr.getInstance().hget(key, field);
//        }
//        return null;
//
//    }
//
//
//    @Override
//    public Long del(String... keys) {
//        return isCluster ? RedisClusterMgr.getInstance().del(keys)
//                : RedisAloneMgr.getInstance().del(keys);
//    }
//
//    @Override
//    public Long hincrBy(String key, String field, Long value) {
//        log.debug("hincrBy key={},field={}, value={}", key, field, value);
//        return isCluster ? RedisClusterMgr.getInstance().hincrBy(key, field, value)
//                : RedisAloneMgr.getInstance().hincrBy(key, field, value);
//    }
//
//    @Override
//    public Object hincrByEx(String key, String field, Long value, Long expire) {
//        log.debug("hincrByEx key={},field={}, value={}", key, field, value);
//        if (StringUtils.isNotBlank(field)) {
//            return isCluster ? RedisClusterMgr.getInstance().hincrByEx(key, field, value, expire)
//                    : RedisAloneMgr.getInstance().hincrByEx(key, field, value, expire);
//        }
//        return null;
//
//    }
//
//    @Override
//    public Set<String> hkeys(String key) {
//        return isCluster ? RedisClusterMgr.getInstance().hkeys(key)
//                : RedisAloneMgr.getInstance().hkeys(key);
//    }
//
//    @Override
//    public String load(String script) {
//        return null;
//    }
//
//    @Override
//    public Long zadd(String key, String value, Double score, Integer expire) {
//        return isCluster ? RedisClusterMgr.getInstance().zadd(key, value, score, expire)
//                : RedisAloneMgr.getInstance().zadd(key, value, score, expire);
//    }
//
//    @Override
//    public Long zcount(String key, Double min, Double max) {
//        return isCluster ? RedisClusterMgr.getInstance().zcount(key, min, max)
//                : RedisAloneMgr.getInstance().zcount(key, min, max);
//    }
//
//    @Override
//    public Long zremrangeByScore(String key, Double min, Double max) {
//        return isCluster ? RedisClusterMgr.getInstance().zremrangeByScore(key, min, max)
//                : RedisAloneMgr.getInstance().zremrangeByScore(key, min, max);
//    }
//
//
//}
