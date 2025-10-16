package com.skydiveforecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.skydiveforecast.domain.model")
@EnableJpaRepositories("com.skydiveforecast.infrastructure.adapter.out.persistance")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
