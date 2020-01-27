package com.subscriber;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestConfiguration.class)
public class DefaultInitializeCacheTest {
    @Autowired
    private ICacheService cacheService;

    @Autowired
    private InitializeCache initializeCache;

    @BeforeEach
    void setUp() {
        cacheService.clearCache();
    }

    @AfterEach
    void tearDown() {
       cacheService.clearCache();
    }

    @SneakyThrows
    @Test
    void should_be_write_data_from_file_to_cache(){
        initializeCache.run();
        Assertions.assertThat(cacheService.getCache()).isNotEmpty();
    }
}
