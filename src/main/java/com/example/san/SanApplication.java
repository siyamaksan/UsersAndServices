package com.example.san;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.san")
public class SanApplication {

    public static void main(String[] args) {
        SpringApplication.run(SanApplication.class, args);
    }

}
