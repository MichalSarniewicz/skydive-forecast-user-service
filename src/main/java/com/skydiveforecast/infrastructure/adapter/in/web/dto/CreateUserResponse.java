package com.skydiveforecast.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {
    private boolean success;
    private String message;
    private Map<String, String> errors;
    private UserDto user;

    public static CreateUserResponse success(String message, UserDto user) {
        return new CreateUserResponse(true, message, null, user);
    }

    public static CreateUserResponse error(String errorMessage) {
        return new CreateUserResponse(false, null,
                Collections.singletonMap("global", errorMessage), null);
    }

    public static CreateUserResponse error(Map<String, String> errors) {
        return new CreateUserResponse(false, null, errors, null);
    }
}