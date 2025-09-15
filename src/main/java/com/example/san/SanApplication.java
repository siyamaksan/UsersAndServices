package com.example.san;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EntityScan("com.example.san")
@EnableCaching
public class SanApplication {

    public static void main(String[] args) {
        SpringApplication.run(SanApplication.class, args);
    }

}
