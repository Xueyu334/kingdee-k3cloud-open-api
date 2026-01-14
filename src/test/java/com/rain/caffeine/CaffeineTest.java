package com.rain.caffeine;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CaffeineTest {


    @Test
    void test() throws InterruptedException {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .build();
        cache.put("as1", "as");
        Thread.sleep(1000);
        Object as1 = cache.get("as1", k -> null);
        log.info(as1.toString());
    }
}
