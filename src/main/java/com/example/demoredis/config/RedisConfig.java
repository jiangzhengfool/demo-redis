package com.example.demoredis.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableCaching
public class RedisConfig {


	@Bean(name = "redisConnectionFactory")
	RedisConnectionFactory connectionFactory() {
//		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//		redisStandaloneConfiguration.setHostName(host);
//		redisStandaloneConfiguration.setPort(port);
//		redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
//		redisStandaloneConfiguration.setDatabase(database);
//

		RedisClusterConfiguration configuration = new RedisClusterConfiguration();
		RedisNode redisNode = new RedisNode("192.168.60.53", 6379);
//		RedisNode redisNode1 = new RedisNode("192.168.60.53", 6380);
//		RedisNode redisNode2 = new RedisNode("192.168.60.53", 6381);
		configuration.setMaxRedirects(3);


		configuration.setClusterNodes(Arrays.asList(new RedisNode[]{redisNode}));
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(configuration);
		jedisConnectionFactory.setTimeout(5000);
		return jedisConnectionFactory;
	}

	@Bean(name = "redisTemplate1")
	public RedisTemplate<Object, Object> redisTemplate(@Qualifier("redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		//使用 fastjson 序列化
//		JacksonRedisSerializer jacksonRedisSerializer = new JacksonRedisSerializer<>(Object.class);
		// value 值的序列化采用 fastJsonRedisSerializer
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		// key 的序列化采用 StringRedisSerializer
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());


		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

//    /**
//     * 默认情况下的模板只能支持RedisTemplate<String, String>，也就是只能存入字符串，因此支持序列化
//     */
//    @Bean
//    @Primary
//    public RedisTemplate<String, Serializable> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
//
//        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
//        template.setEnableDefaultSerializer(false);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setConnectionFactory(redisConnectionFactory);
//
//        return template;
//    }

//    @Bean
//    public RedisCacheWriter redisCacheWriter(RedisConnectionFactory redisConnectionFactory) {
//        return RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory);
//    }


}
