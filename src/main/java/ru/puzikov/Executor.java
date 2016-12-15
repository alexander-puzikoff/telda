package ru.puzikov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ru.puzikov.config.JPAConfig;

@SpringBootApplication
public class Executor {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{Executor.class, JPAConfig.class}, args);
    }
}
