package com.example.demoredis.jedis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author llf
 * @program: svc_epc_plugin_risk_rule
 * @desc redis集群
 * @date 2022/11/7 14:23
 */
@Slf4j
public class RedisCluster {


    /**
     * 集群
     *
     * @param redisHost
     * @return
     */
    public JedisCluster getJedisCluster(String redisHost, String pwd) {

        Set<HostAndPort> hostAndPortsSet = new HashSet<HostAndPort>();
        String[] redisInstances = redisHost.split(",");
        for (int i = 0; i < redisInstances.length; i++) {
            String redisInstance = redisInstances[i];
            String ip = redisInstance.split(":")[0];
            Integer port = Integer.parseInt(redisInstance.split(":")[1]);
            // 添加节点
            hostAndPortsSet.add(new HostAndPort(ip, port));
        }
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(500);
        // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(5);

        // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(100 * 1000);
        // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(true);
        log.info("初始化集群Redis pool,配置参数,redisHost={}，config={}", redisHost, config);
        return new JedisCluster(hostAndPortsSet, 10000, 10000, 10, pwd, config);

    }
}
