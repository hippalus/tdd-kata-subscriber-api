package com.subscriber.service;


import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.subscriber.model.Subscriber;
import com.subscriber.exception.SubscriberNotFoundException;
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

    @Override
    public void updateCache(Subscriber subscriber) {
        checkIfNotExistThrowException(subscriber.getId());
        getCache().put(subscriber.getId(), subscriber);

    }

    @Override
    public void deleteCache(Long id) {
        checkIfNotExistThrowException(id);
        getCache().delete(id);
    }

    @Override
    public Subscriber getById(Long id) {
        checkIfNotExistThrowException(id);
        return getCache().get(id);

    }

    @Override
    public void clearCache() {
        getCache().clear();
    }

    private void checkIfNotExistThrowException(Long id) {
        if (!getCache().containsKey(id)) {
            throw new SubscriberNotFoundException("Subscriber Not Found");
        }
    }
}
