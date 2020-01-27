package com.subscriber.service;

import com.hazelcast.core.IMap;
import com.subscriber.model.Subscriber;

public interface ICacheService {
    void addToCache(Long id, Subscriber object);

    IMap<Long, Subscriber> getCache();

    void updateCache(Subscriber subscriber);

    void deleteCache(Long id);

    void clearCache();

    Subscriber getById(Long id);
}
