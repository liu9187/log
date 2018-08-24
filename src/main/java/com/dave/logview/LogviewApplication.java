package com.dave.logview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.dave")
public class LogviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogviewApplication.class, args);
    }
}
