package com.skydiveforecast;

import org.springframework.boot.SpringApplication;

public class TestSkydiveForecastApplication {

	public static void main(String[] args) {
		SpringApplication.from(SkydiveForecastApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
