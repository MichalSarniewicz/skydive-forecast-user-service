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
public class UserStatusUpdateResponse {
    private boolean success;
    private String message;
    private Map<String, String> errors;
    
    public static UserStatusUpdateResponse success(String message) {
        UserStatusUpdateResponse response = new UserStatusUpdateResponse();
        response.setSuccess(true);
        response.setMessage(message);
        return response;
    }
    
    public static UserStatusUpdateResponse error(String message) {
        UserStatusUpdateResponse response = new UserStatusUpdateResponse();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
    
    public static UserStatusUpdateResponse error(Map<String, String> errors) {
        UserStatusUpdateResponse response = new UserStatusUpdateResponse();
        response.setSuccess(false);
        response.setErrors(errors);
        return response;
    }
}
