package com.fenixcommunity.centralspace.domain.core.redis;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor(access = PACKAGE) @FieldDefaults(level = PRIVATE, makeFinal = true)
public class RedisService {
    public static final String SPRING_SESSION_PATTERN = "spring.*";
    private final RedisTemplate<String, Object> redisTemplate;

    public Set<String> getRedisKeys(final String finderPattern) {
        return redisTemplate.keys(finderPattern);
    }

    public Set<String> getUsersSessionKeys() {
        return redisTemplate.keys(SPRING_SESSION_PATTERN);
    }


}
