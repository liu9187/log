package com.dave.logview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.dave")
public class LogViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogViewApplication.class, args);
    }

}
