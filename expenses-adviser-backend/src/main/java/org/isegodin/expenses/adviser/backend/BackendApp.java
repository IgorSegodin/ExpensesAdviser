package org.isegodin.expenses.adviser.backend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author isegodin
 */
@SpringBootApplication(
        scanBasePackages = "org.isegodin.expenses.adviser.backend"
)
public class BackendApp {

    public static void main(String[] args) {
        SpringApplication.run(BackendApp.class, args);
    }

}
