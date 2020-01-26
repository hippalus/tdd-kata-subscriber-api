package com.subscriber;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscribersList implements Serializable {

    private List<Subscriber> subscribers;

    public List<Subscriber> getSubscribers() {
        return this.subscribers;
    }
}
