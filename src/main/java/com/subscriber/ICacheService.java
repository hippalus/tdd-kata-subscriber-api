package com.subscriber;

import com.hazelcast.core.IMap;

public interface ICacheService {
    void addToCache(Long id, Subscriber object);

    IMap<Long, Subscriber> getCache();

    void updateCache(Subscriber subscriber);

    void deleteCache(Long id);

    void clearCache();

    Subscriber getById(Long id);
}
