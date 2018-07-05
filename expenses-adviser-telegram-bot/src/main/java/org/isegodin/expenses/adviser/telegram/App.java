package org.isegodin.expenses.adviser.telegram;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * @author isegodin
 */
@Configuration
@ComponentScan({"org.isegodin.expenses.adviser.telegram"})
@PropertySources({
        @PropertySource("org/isegodin/expenses/adviser/telegram/telegram-bot.properties")
})
public class App {

    private static ApplicationContext context;

    public static void main(String[] args) {
        App.context = new AnnotationConfigApplicationContext(App.class);
    }
}
