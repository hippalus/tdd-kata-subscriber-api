package com.subscriber;


import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CacheService implements ICacheService {

    private HazelcastInstance hazelcastInstance;

    public CacheService(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public void addToCache(Long id, Subscriber subscriber) {
        getCache().put(id, subscriber);
    }

    @Override
    public IMap<Long, Subscriber> getCache() {
        return hazelcastInstance.getMap("subscribers");
    }
}
