package com.skydiveforecast.infrastructure.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public Counter loginSuccessCounter(MeterRegistry registry) {
        return Counter.builder("user.login.success")
                .description("Total number of successful logins")
                .tag("service", "user")
                .register(registry);
    }

    @Bean
    public Counter loginFailureCounter(MeterRegistry registry) {
        return Counter.builder("user.login.failure")
                .description("Total number of failed login attempts")
                .tag("service", "user")
                .register(registry);
    }

    @Bean
    public Counter tokenRefreshCounter(MeterRegistry registry) {
        return Counter.builder("user.token.refresh")
                .description("Total number of token refresh requests")
                .tag("service", "user")
                .register(registry);
    }

    @Bean
    public Counter userCreationCounter(MeterRegistry registry) {
        return Counter.builder("user.creation")
                .description("Total number of users created")
                .tag("service", "user")
                .register(registry);
    }

    @Bean
    public Timer authenticationTimer(MeterRegistry registry) {
        return Timer.builder("user.authentication.time")
                .description("Time taken to authenticate users")
                .tag("service", "user")
                .register(registry);
    }
}
