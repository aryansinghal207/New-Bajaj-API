package com.bfhl.api;

import com.bfhl.api.config.UserProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(UserProperties.class)
public class BfhlApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BfhlApiApplication.class, args);
    }
}
