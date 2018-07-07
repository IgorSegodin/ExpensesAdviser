package org.isegodin.expenses.adviser.telegram.config;

import org.isegodin.expenses.adviser.backend.api.UserServiceApi;
import org.isegodin.expenses.adviser.backend.api.impl.UserServiceApiImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author isegodin
 */
@Configuration
public class ServiceConfig {

    @Bean
    public UserServiceApi userServiceApi(@Value("${expenses-adviser.backend.url}") String baseUrl) {
        return new UserServiceApiImpl(baseUrl);
    }
}
