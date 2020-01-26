package com.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class FileOperationsComponent {

    @Value("${subscriber.data.file.path}")
    private String filePath;

    private final ObjectMapper objectMapper;

    public FileOperationsComponent(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public String getFilePath() {
        return this.filePath;
    }


    public <T> Object writeToPojo(String filePath, Class<T> clazz) {
        T result = null;
        try {
            result = objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            log.error(String.format("Invalid File Path : %s or Invalid class : %s ", filePath, clazz));
        }
        return result;
    }
}
