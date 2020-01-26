package com.subscriber;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
public class FileOperationsComponentTest {
    @Autowired
    private FileOperationsComponent fileOperationsComponent;



    @Test
    void should_take_the_data_file_path_value_that_it_reads_from_the_application_properties_file() {
        //when:
        String filePath = fileOperationsComponent.getFilePath();

        //then:
        assertThat(filePath).isEqualTo("/home/hisler/Workspace/kata-subscriber-api/src/test/resources/data.json");
    }

    @Test
//_and_cache its contents()
    void should_read_the_file_of_this_value_map_to_pojo() {
        //given:
        String filePath = fileOperationsComponent.getFilePath();
        //when:
        SubscribersList subscribersList= (SubscribersList) fileOperationsComponent.writeToPojo(filePath,SubscribersList.class);
        //then:
        assertThat(subscribersList.getSubscribers()).isEqualTo(getMockSubscribersList().getSubscribers());
    }



    private SubscribersList getMockSubscribersList() {
        List<Subscriber> subscribers=new ArrayList<>();
        subscribers.add(new Subscriber(1L, "Stephan King", "905552551122"));
        subscribers.add(new Subscriber(2L, "Alice Gracy", "905552551133"));
        subscribers.add(new Subscriber(3L, "Glory Wisdom", "905552551144"));
        SubscribersList mockSubscribers = new SubscribersList();
        mockSubscribers.setSubscribers(subscribers);
        return mockSubscribers;
    }
}
