package com.subscriber.service;

import com.subscriber.exception.SubscriberNotFoundException;
import com.subscriber.model.Subscriber;
import com.subscriber.model.SubscribersList;
import com.subscriber.service.FileOperationsComponent;
import com.subscriber.service.ICacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class CacheServiceTest {

    @Autowired
    private FileOperationsComponent fileOperationsComponent;
    @Autowired
    private ICacheService cacheService;

    @BeforeEach
    void tearDown(){
        cacheService.clearCache();
    }

    @Test
    void should_file_content_add_to_cache(){

        //given:
        SubscribersList subscribersList= (SubscribersList) fileOperationsComponent.writeToPojo(SubscribersList.class);

        // when:
        subscribersList.getSubscribers()
                .forEach(subscriber -> {
                    cacheService.addToCache(subscriber.getId(),subscriber);
                });

        //then:
        assertThat(cacheService.getCache()).isNotEmpty();
    }


    @Test
    void should_update_subscriber(){

        //given:
        Subscriber subscriber = new Subscriber(5L,"hakan","9043245145");
        cacheService.addToCache(subscriber.getId(),subscriber);

        // when:

        Subscriber updatedSubscriber = new Subscriber(5L,"habiphakan","9043245145");
        cacheService.updateCache(updatedSubscriber);

        //then:
        assertThat(cacheService.getCache().get(5L)).isEqualToComparingFieldByField(updatedSubscriber);
    }
    @Test
    void should_throw_SubscriberNotFoundException_if_subscriber_not_exist_in_cache_when_try_to_update(){

        // given:
        Subscriber updatedSubscriber = new Subscriber(5L,"habiphakan","9043245145");

        // when then:
        assertThatThrownBy(() -> cacheService.updateCache(updatedSubscriber))
                .isInstanceOf(SubscriberNotFoundException.class)
                .hasMessage("Subscriber Not Found");

    }

    @Test
    void should_delete_subscriber(){

        //given:
        Subscriber subscriber = new Subscriber(5L,"hakan","9043245145");
        cacheService.addToCache(subscriber.getId(),subscriber);

        // when:
        cacheService.deleteCache(subscriber.getId());

        //then:
        assertThat(cacheService.getCache()).isEmpty();
    }
    @Test
    void should_throw_SubscriberNotFoundException_if_subscriber_not_exist_in_cache_when_try_to_delete(){
        // when then:
        assertThatThrownBy(() -> cacheService.deleteCache(5L))
                .isInstanceOf(SubscriberNotFoundException.class)
                .hasMessage("Subscriber Not Found");

    }
}
