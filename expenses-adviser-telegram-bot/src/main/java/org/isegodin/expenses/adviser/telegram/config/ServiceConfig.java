package org.isegodin.expenses.adviser.telegram.config;

import org.isegodin.expenses.adviser.telegram.bot.service.TelegramService;
import org.isegodin.expenses.adviser.telegram.bot.service.impl.TelegramServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author i.segodin
 */
@Configuration
public class ServiceConfig {

    @Bean
    public TelegramService telegramService(@Value("${telegram.token}") String token) {
        return new TelegramServiceImpl(token);
    }
}
