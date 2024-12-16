package org.example.firstlabis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class FirstLabIsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstLabIsApplication.class, args);
    }

}
