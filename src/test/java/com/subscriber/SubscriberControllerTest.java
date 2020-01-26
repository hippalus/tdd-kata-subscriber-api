package com.subscriber;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@WebAppConfiguration
public class SubscriberControllerTest {

    @Autowired
    private SubscriberController subscriberController;
    @Autowired
    private FileOperationsComponent fileOperationsComponent;

    @Autowired
    WebApplicationContext webApplicationContext;
    protected MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        fileOperationsComponent.getCacheService().clearCache();
    }

    @Test
    void contextLoads() {
        Assertions.assertThat(subscriberController).isNotNull();
    }

    @Test
    void should_save_subscriber_into_cache_and_return_200OK() throws Exception {

        //given
        Subscriber subscriber = new Subscriber(4L, "habip", "905061623363");
        String uri = "/subscriber";

        //when
        String inputJson = fileOperationsComponent.pojoToJson(subscriber);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();
        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(inputJson, content);
    }

    @Test
    void should_update_subscriber_into_cache_and_return_200OK() throws Exception {

        //given
        Subscriber subscriber = new Subscriber(4L, "habip_updated", "905061623363");
        fileOperationsComponent.getCacheService().addToCache(subscriber.getId(), subscriber);
        String uri = "/subscriber";

        //when
        String inputJson = fileOperationsComponent.pojoToJson(subscriber);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();
        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(inputJson, content);
    }

    @Test
    void should_throw_SubscriberNotFoundException_if_subscriber_not_exist_in_cache_when_try_to_update() throws Exception {

        //given
        Subscriber subscriber = new Subscriber(4L, "habip_updated", "905061623363");
        String uri = "/subscriber";

        //when
        String inputJson = fileOperationsComponent.pojoToJson(subscriber);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson))
                .andReturn();
        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Subscriber Not Found", content);
    }


    @Test
    void should_delete_subscriber_from_cache_and_return_200OK() throws Exception {

        //given
        Subscriber subscriber = new Subscriber(1L, "habip_deleted", "905061623363");
        fileOperationsComponent.getCacheService().addToCache(subscriber.getId(), subscriber);
        String uri = "/subscriber/1";

        //when
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();

        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Subscriber is deleted successfully", content);
    }

    @Test
    void should_throw_SubscriberNotFoundException_if_subscriber_not_exist_in_cache_when_try_to_delete() throws Exception {
        //given
        String uri = "/subscriber/1";

        //when
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();

        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Subscriber Not Found", content);
    }

    @Test
    void should_get_all_subscribers_from_cache() throws Exception {
        //given
        Subscriber subscriber = new Subscriber(4L, "habip", "905061623363");
        Subscriber subscriber1 = new Subscriber(6L, "murtaza", "905146546525");
        fileOperationsComponent.getCacheService().addToCache(subscriber.getId(), subscriber);
        fileOperationsComponent.getCacheService().addToCache(subscriber1.getId(), subscriber1);
        String uri = "/subscriber/getAllSubscribers";

        //when
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String resultJson = fileOperationsComponent.pojoToJson(new SubscribersList(Arrays.asList(subscriber, subscriber1)));
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(resultJson, content);
    }

    @Test
    void should_empty_list_if_cache_is_empty_when_call_get_all_subscribers() throws Exception {
        //given
        String uri = "/subscriber/getAllSubscribers";

        //when
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        String resultJson = fileOperationsComponent.pojoToJson(new SubscribersList(new ArrayList<>()));
        assertEquals(resultJson, content);
    }

    @Test
    void should_get_subscriber_by_id_from_cache_when_call_getSubscriberById() throws Exception {

        //given
        Subscriber subscriber = new Subscriber(4L, "habip", "905061623363");
        Subscriber subscriber1 = new Subscriber(6L, "murtaza", "905146546525");
        fileOperationsComponent.getCacheService().addToCache(subscriber.getId(), subscriber);
        fileOperationsComponent.getCacheService().addToCache(subscriber1.getId(), subscriber1);
        String uri = "/subscriber/getSubscriberById/4";
        String uri2 = "/subscriber/getSubscriberById/6";

        //when
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)).andReturn();

        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String resultJson = fileOperationsComponent.pojoToJson(subscriber);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(resultJson, content);

        //when
        MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.post(uri2)).andReturn();

        //then
        int status2 = mvcResult2.getResponse().getStatus();
        assertEquals(200, status2);
        String resultJson2 = fileOperationsComponent.pojoToJson(subscriber1);
        String content2 = mvcResult2.getResponse().getContentAsString();
        assertEquals(resultJson2, content2);

    }
    @Test
    void should_return_SubscriberNotFound_when_call_getSubscriberById() throws Exception {
        //given
        String uri = "/subscriber/getSubscriberById/4";

        //when
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)).andReturn();

        //then
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("Subscriber Not Found", content);

    }

}
