package ru.puzikov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.puzikov.config.JpaConfig;

@SpringBootApplication
public class Executor {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{Executor.class, JpaConfig.class}, args);
    }
}