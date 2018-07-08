package org.isegodin.expenses.adviser.telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author isegodin
 */
@SpringBootApplication(
        scanBasePackages = "org.isegodin.expenses.adviser.telegram"
)
public class TelegramApp {

    public static void main(String[] args) {
        SpringApplication.run(TelegramApp.class, args);
    }
}
