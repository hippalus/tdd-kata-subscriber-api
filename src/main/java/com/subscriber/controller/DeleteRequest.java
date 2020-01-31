package com.subscriber.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRequest {
    private String id;


    public Long getLongValue(){
        return Long.parseLong(this.id);
    }
}
