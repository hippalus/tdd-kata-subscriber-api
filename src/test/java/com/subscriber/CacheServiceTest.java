package com.subscriber;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class CacheServiceTest {

    @Autowired
    private FileOperationsComponent fileOperationsComponent;
    @Autowired
    private ICacheService cacheService;

    @Test
    void should_file_content_add_to_cache(){

        //given:
        String filePath = fileOperationsComponent.getFilePath();
        SubscribersList subscribersList= (SubscribersList) fileOperationsComponent.writeToPojo(filePath,SubscribersList.class);
        // when:
        subscribersList.getSubscribers()
                .forEach(subscriber -> {
                    cacheService.addToCache(subscriber.getId(),subscriber);
                });
        assertThat(cacheService.getCache()).isNotEmpty();
        assertThat(cacheService.getCache().values()).contains(subscribersList.getSubscribers().get(1));
    }
}
