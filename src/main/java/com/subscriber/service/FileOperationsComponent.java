package com.subscriber.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.subscriber.model.SubscribersList;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;


@Component
@Getter
public class FileOperationsComponent {

    private static final Logger log= LoggerFactory.getLogger(FileOperationsComponent.class);
    private final ObjectMapper objectMapper;
    private ICacheService cacheService;

    @Value("${subscriber.data.file.path}")
    private String filePath;

    public FileOperationsComponent(ObjectMapper objectMapper, ICacheService cacheService) {
        this.objectMapper = objectMapper;
        this.cacheService = cacheService;
    }

    public String getFilePath() {
        return this.filePath;
    }


    public <T> Object writeToPojo(Class<T> clazz) {
        T result = null;
        try {

            result = objectMapper.readValue(ResourceUtils.getFile(filePath), clazz);
        } catch (IOException e) {
            log.error(String.format("Invalid File Path : %s or Invalid class : %s ", filePath, clazz));
            log.error(e.getMessage());
        }
        return result;
    }

    public String pojoToJson(Object object) {
        String result = null;
        try {
            result = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(String.format("Invalid Object : %s ", object.getClass().getName()));
        }
        return result;
    }

    @Scheduled(cron = "${subscriber.data.update.scheduler}")
    public void writeToFile() {
        SubscribersList subscribersList = new SubscribersList(new ArrayList<>(cacheService.getCache().values()));
        try {
            Files.write(ResourceUtils.getFile(filePath).toPath(), pojoToJson(subscribersList).getBytes());
            if (log.isInfoEnabled()) {
                log.info("The cache content written in to the file:{}", getFilePath());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
