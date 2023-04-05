package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ReactiveApplicationDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplicationDemoApplication.class, args);
    }
}
