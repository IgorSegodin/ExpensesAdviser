package org.isegodin.expenses.adviser.telegram.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.isegodin.expenses.adviser.telegram.service.JsonService;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

/**
 * @author i.segodin
 */
@Component
public class JsonServiceImpl implements JsonService {

    private final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Override
    @SneakyThrows
    public String toJson(Object o) {
        StringWriter writer = new StringWriter();
        objectMapper.writeValue(writer, o);
        return writer.toString();
    }

    @Override
    @SneakyThrows
    public <T> T fromJson(String json, Class<T> type) {
        return objectMapper.readValue(json, type);
    }
}
