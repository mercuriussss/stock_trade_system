package com.caijianlong.stock_trade_system.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
    private static final StringRedisSerializer STRING_SERIALIZER = new StringRedisSerializer();
    private static final GenericJackson2JsonRedisSerializer JACKSON__SERIALIZER = new GenericJackson2JsonRedisSerializer();

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        //设置缓存过期时间
        RedisCacheConfiguration redisCacheCfg=RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(STRING_SERIALIZER))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(JACKSON__SERIALIZER));
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheCfg)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        // key序列化
        redisTemplate.setKeySerializer(STRING_SERIALIZER);
        // value序列化
        redisTemplate.setValueSerializer(JACKSON__SERIALIZER);
        // Hash key序列化
        redisTemplate.setHashKeySerializer(STRING_SERIALIZER);
        // Hash value序列化
        redisTemplate.setHashValueSerializer(JACKSON__SERIALIZER);
        // 开启事务支持
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
