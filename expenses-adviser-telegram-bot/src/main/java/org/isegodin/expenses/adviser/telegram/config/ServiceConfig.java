package org.isegodin.expenses.adviser.telegram.config;

import org.isegodin.expenses.adviser.backend.api.PaymentServiceApi;
import org.isegodin.expenses.adviser.backend.api.UserServiceApi;
import org.isegodin.expenses.adviser.backend.api.impl.PaymentServiceApiImpl;
import org.isegodin.expenses.adviser.backend.api.impl.UserServiceApiImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author isegodin
 */
@Configuration
public class ServiceConfig {

    @Value("${expenses-adviser.backend.url}")
    private String baseUrl;

    @Value("${expenses-adviser.backend.accessToken}")
    private String accessToken;

    @Bean
    public UserServiceApi userServiceApi() {
        return new UserServiceApiImpl(baseUrl, accessToken);
    }

    @Bean
    public PaymentServiceApi paymentServiceApi() {
        return new PaymentServiceApiImpl(baseUrl, accessToken);
    }
}
