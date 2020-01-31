package com.subscriber.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeleteRequestTest {
    @Test
    void should_get_Long_value() {
        DeleteRequest deleteRequest = new DeleteRequest("1");
        Assertions.assertThat(deleteRequest.getLongValue()).isEqualTo(1);
    }
}
