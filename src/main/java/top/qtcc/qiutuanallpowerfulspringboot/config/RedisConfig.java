package top.qtcc.qiutuanallpowerfulspringboot.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 *  Redis 配置类
 *
 * @author qiutuan
 * @date 2024/12/07
 */
@EnableCaching
@Configuration
public class RedisConfig {

    // 配置 RedisTemplate Bean
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // 使用 StringRedisSerializer 来序列化和反序列化 redis 的 key 值
        template.setKeySerializer(new StringRedisSerializer());
        // 使用 GenericJackson2JsonRedisSerializer 来序列化和反序列化 redis 的 value 值
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

    // 配置 CacheManager Bean
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)) // 缓存过期时间 1 小时
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues(); // 禁用缓存空值

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }

    // 配置 RedisCacheConfiguration Bean
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)) // 缓存过期时间 1 小时
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues() // 禁用缓存空值
                .prefixCacheNameWith("cache:"); // 缓存名称前缀
    }
}