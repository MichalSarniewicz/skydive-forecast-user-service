package com.skydiveforecast.adapter.in.web;

import com.skydiveforecast.application.ForecastService;
import com.skydiveforecast.domain.model.Forecast;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/forecast")
public class ForecastController {

    private final ForecastService forecastService;

    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping
    public Forecast getForecast(
            @RequestParam("lat") double latitude,
            @RequestParam("lon") double longitude,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return forecastService.getForecast(latitude, longitude, date);
    }
}
