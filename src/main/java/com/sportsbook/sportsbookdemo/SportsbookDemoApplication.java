package com.sportsbook.sportsbookdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
        )
public class SportsbookDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportsbookDemoApplication.class, args);
    }

}
