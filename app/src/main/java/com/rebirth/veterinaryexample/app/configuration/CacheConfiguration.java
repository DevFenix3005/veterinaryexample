package com.rebirth.veterinaryexample.app.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@Slf4j
public class CacheConfiguration {

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .initialCapacity(1_000)
                .maximumSize(10_000)
                .removalListener((key, value, cause) -> log.info("Key {} was removed ({})", key, cause))
                .expireAfterWrite(20, TimeUnit.MINUTES)
                .recordStats();
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager caffeineCacheManager =
                new CaffeineCacheManager("pets", "listOfPets", "breeds", "listOfbreeds", "details");
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

}
