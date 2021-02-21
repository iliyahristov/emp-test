package com.ih;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(com.ih.WebApplication.class, args);
    }
}
