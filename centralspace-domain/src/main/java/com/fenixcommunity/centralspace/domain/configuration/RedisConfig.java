package com.fenixcommunity.centralspace.domain.configuration;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories(basePackages = "com.fenixcommunity.centralspace.domain.repository.memory.redis")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RedisConfig {

    @Bean
    RedisConnectionFactory redisConnectionFactory(@Value("${redis.hostName}") final String hostName,
                                                  @Value("${redis.port}") final int port,
                                                  @Value("${redis.jedisAvailable}") final boolean jedisAvailable,
                                                  @Value("${redis.lettuceAvailable}") final boolean lettuceAvailable) {
        final RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(hostName, port);
        if (jedisAvailable) {
            return new JedisConnectionFactory(configuration);
        } else if (lettuceAvailable) {
            return new LettuceConnectionFactory(configuration);
        } else {
            throw new IllegalArgumentException("No Jedis or lettuce client on classpath. "
                    + "Please add one of the implementation to your classpath");
        }
    }

    @Bean
    @DependsOn("redisConnectionFactory")
    public RedisTemplate<String, Object> redisTemplate(final RedisConnectionFactory redisConnectionFactory) {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
