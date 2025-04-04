package com.ensa.miniproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()

public class MiniProjectApplication implements CommandLineRunner {

    @Value("${jwt.secret-key}")
    private String jwtSecret;
    public static void main(String[] args) {
        SpringApplication.run(MiniProjectApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("JWT Secret: " + (jwtSecret != null ? jwtSecret : "Missing"));
    }
}
