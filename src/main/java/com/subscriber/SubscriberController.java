package com.subscriber;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class SubscriberController {

    private final ICacheService cacheService;

    public SubscriberController(ICacheService cacheService) {
        this.cacheService = cacheService;
    }

    @PostMapping("/subscriber")
    public ResponseEntity<Subscriber> saveSubscriber(@RequestBody Subscriber subscriber) {
        cacheService.addToCache(subscriber.getId(), subscriber);

        return ResponseEntity.ok(subscriber);

    }

    @PutMapping("/subscriber")
    public ResponseEntity<Subscriber> updateSubscriber(@RequestBody Subscriber subscriber){

        cacheService.updateCache(subscriber);
        return ResponseEntity.ok(subscriber);

    }
    @DeleteMapping("/subscriber/{id}")
    public ResponseEntity<String> deleteSubscriber(@PathVariable("id") Long id){

        cacheService.deleteCache(id);
        return ResponseEntity.ok("Subscriber is deleted successfully");

    }
    @GetMapping("/subscriber/getAllSubscribers")
    public ResponseEntity<SubscribersList> getAllSubscribers(){
        return ResponseEntity.ok(new SubscribersList(new ArrayList<>(cacheService.getCache().values())));
    }
    @PostMapping("/subscriber/getSubscriberById/{id}")
    public ResponseEntity<Subscriber> saveSubscriber(@PathVariable("id") Long id) {
        Subscriber subscriber=cacheService.getById(id);
        return ResponseEntity.ok(subscriber);
    }
}
