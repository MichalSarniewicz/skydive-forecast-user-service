package com.skydiveforecast.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {
    private boolean success;
    private String message;
    private UserDto user;
    private Map<String, String> errors;
    
    public static UpdateUserResponse success(String message, UserDto user) {
        UpdateUserResponse response = new UpdateUserResponse();
        response.setSuccess(true);
        response.setMessage(message);
        response.setUser(user);
        return response;
    }
    
    public static UpdateUserResponse error(String message) {
        UpdateUserResponse response = new UpdateUserResponse();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
    
    public static UpdateUserResponse error(Map<String, String> errors) {
        UpdateUserResponse response = new UpdateUserResponse();
        response.setSuccess(false);
        response.setErrors(errors);
        return response;
    }
}
