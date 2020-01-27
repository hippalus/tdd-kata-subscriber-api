package com.subscriber;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class FileOperationsComponentTest {
    @Autowired
    private FileOperationsComponent fileOperationsComponent;

    @BeforeEach
    void setup(){
        fileOperationsComponent.getCacheService().clearCache();
    }

    @Test
    void should_take_the_data_file_path_value_that_it_reads_from_the_application_properties_file() {
        //when:
        String filePath = fileOperationsComponent.getFilePath();

        //then:
        assertThat(filePath).isEqualTo("classpath:data.json");
    }

    @Test
    void should_read_the_file_of_this_value_map_to_pojo() {

        //when:
        SubscribersList subscribersList = getSubscribersList();
        //then:
        assertThat(subscribersList.getSubscribers()).containsExactlyInAnyOrderElementsOf(getMockSubscribersList().getSubscribers());
    }

    @Test
    void should_be_file_update_according_to_the_cron_expression() {
        //given:
        SubscribersList subscribersList = getSubscribersList();

        //when:
        Awaitility.await().atMost(2, TimeUnit.MINUTES);

        //then:
        assertThat(subscribersList.getSubscribers()).containsExactlyInAnyOrderElementsOf(getMockSubscribersList().getSubscribers());

    }

    private SubscribersList getSubscribersList() {
        return (SubscribersList) fileOperationsComponent.writeToPojo(SubscribersList.class);
    }

    private SubscribersList getMockSubscribersList() {
        List<Subscriber> subscribers = new ArrayList<>();
        subscribers.add(new Subscriber(1L, "Stephan King", "905552551122"));
        subscribers.add(new Subscriber(2L, "Alice Gracy", "905552551133"));
        subscribers.add(new Subscriber(3L, "Glory Wisdom", "905552551144"));
        SubscribersList mockSubscribers = new SubscribersList();
        mockSubscribers.setSubscribers(subscribers);
        return mockSubscribers;
    }
}
