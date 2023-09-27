//package com.example.demoredis.jedis;
//
//import lombok.extern.slf4j.Slf4j;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisCluster;
//
//import java.util.Properties;
//
///**
// * @author llf
// * @program: svc_epc_plugin_risk_rule
// * @desc
// * @date 2022/11/7 18:14
// */
//@Slf4j
//public class RedisBase  {
//
//    protected static final String REDIS_PASSWORD_CLUSTER = "Zu5mIJN5AjWHpFv3jAiCPBOJzKnq0bai";
//
//    protected static final String REDIS_PASSWORD_ALONE = "Zu5mIJN5AjWHpFv3jAiCPBOJzKnq0baiZ";
//
//    protected static final String REDIS_HOST_ALONE = "redis_host";
//
//
//    protected static final String REDIS_HOST_CLUSTER = "redis_cluster";
//
//    protected static final String REDIS_PASSWORD_KEY = "redis_password";
//
//    protected static final String REDIS_PORT_KEY = "redis_port";
//
//    protected static final String SCRIPT_HSET_EX =    "local key=KEYS[1];local field=ARGV[1];local  val =ARGV[2];local expired=ARGV[3];  local exists = redis.pcall('exists',key); redis.pcall('HSET',key,field, val);if( exists ~= 1) then redis.pcall('EXPIRE',key,expired) end;";
//
//    protected static final String SCRIPT_HINCR_EX =  "local key=KEYS[1];local field=ARGV[1];local  val =ARGV[2];local expired=ARGV[3];  local exists = redis.pcall('exists',key);   if( exists ~= 1) then redis.pcall('HSET',key,field, 1)  end;  if( exists == 1) then redis.pcall('hincrby',key,field, 1) end;    if( exists ~= 1) then redis.pcall('EXPIRE',key,expired) end;";
//
//    protected static String getProperties(Properties properties, String key, String defaultValue){
//       return  PropUtil.getProperties(properties, key,defaultValue);
//    }
//
//    protected static String getProperties(Properties properties, String key){
//        return  PropUtil.getProperties(properties, key);
//    }
//
//    protected void close(JedisCluster jedisCluster){
//        /*if(jedisCluster != null){
//            try {
//                jedisCluster.close();
//            }catch (Exception e){
//                log.error("close jedis cluster error", e);
//            }
//
//        }*/
//    }
//
//    protected void close(Jedis jedis){
//        if(jedis != null){
//            jedis.close();
//        }
//    }
//
//}
