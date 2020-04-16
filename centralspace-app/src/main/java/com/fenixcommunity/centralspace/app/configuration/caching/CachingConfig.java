package com.fenixcommunity.centralspace.app.configuration.caching;

import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {
    public static final String CENTRALSPACE_CACHE = "centralspace-cache";

    @Bean
    public CacheManager cacheManager() {
        final SimpleCacheManager cacheManager = new SimpleCacheManager();
        final ConcurrentMapCache globalMapCache = new ConcurrentMapCache(CENTRALSPACE_CACHE, false);
        cacheManager.setCaches(List.of(globalMapCache));
        return cacheManager;
    }
}

