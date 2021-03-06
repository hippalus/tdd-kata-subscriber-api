package com.subscriber.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscriber implements Serializable {
    private Long id;
    private String name;
    private String msisdn;

}
