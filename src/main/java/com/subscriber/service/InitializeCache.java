package com.subscriber.service;

import com.subscriber.model.SubscribersList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitializeCache implements CommandLineRunner {

    private FileOperationsComponent fileOperationsComponent;

    public InitializeCache(FileOperationsComponent fileOperationsComponent) {
        this.fileOperationsComponent = fileOperationsComponent;
    }

    @Override
    public void run(String... args) throws Exception {
        SubscribersList subscribersList = (SubscribersList) fileOperationsComponent.writeToPojo(SubscribersList.class);
        if(subscribersList!=null) {
            subscribersList.getSubscribers()
                    .stream()
                    .forEach(subscriber ->
                            fileOperationsComponent
                                    .getCacheService()
                                    .addToCache(subscriber.getId(), subscriber));
        }

    }
}
