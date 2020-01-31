package com.subscriber.controller;

import com.subscriber.model.Subscriber;
import com.subscriber.model.SubscribersList;
import com.subscriber.service.ICacheService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class SubscriberController {

    private final ICacheService cacheService;

    public SubscriberController(ICacheService cacheService) {
        this.cacheService = cacheService;
    }

    @RequestMapping(value = "/subscriber", method = POST)
    public ResponseEntity<Subscriber> saveSubscriber(@RequestBody Subscriber subscriber) {
        cacheService.addToCache(subscriber.getId(), subscriber);
        return ResponseEntity.ok(subscriber);
    }

    @RequestMapping(value = "/subscriber", method = PUT)
    public ResponseEntity<Subscriber> updateSubscriber(@RequestBody Subscriber subscriber) {
        cacheService.updateCache(subscriber);
        return ResponseEntity.ok(subscriber);
    }

    @RequestMapping(value = "/subscriber", method = DELETE)
    public ResponseEntity<String> deleteSubscriber(@RequestBody DeleteRequest deleteRequest) {
        cacheService.deleteCache(deleteRequest.getLongValue());
        return ResponseEntity.ok("Subscriber is deleted successfully");
    }

    @RequestMapping(value = "/subscriber/getAllSubscribers", method = GET)
    public ResponseEntity<SubscribersList> getAllSubscribers() {
        return ResponseEntity.ok(new SubscribersList(new ArrayList<>(cacheService.getCache().values())));
    }

    @RequestMapping(value = "/subscriber/getSubscriberById/{id}", method = POST)
    public ResponseEntity<Subscriber> getSubscriberById(@PathVariable("id") Long id) {
        Subscriber subscriber = cacheService.getById(id);
        return ResponseEntity.ok(subscriber);
    }
}
