package com.sportsbook.sportsbookdemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration(proxyBeanMethods = false)
@Component
@EnableTransactionManagement
@EntityScan(basePackages = "com.sportsbook.sportsbookdemo.entity")
@EnableJpaRepositories(basePackages = "com.sportsbook.sportsbookdemo")

public class SportsbookDemoConfig {
}
