package com.skydiveforecast.domain.service.validation;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PasswordValidatorServiceImpl implements PasswordValidatorService {

    public Map<String, String> validate(String password) {
        Map<String, String> errors = new HashMap<>();
        
        if (password == null || password.isBlank()) {
            errors.put("password", "Password is required");
        } else {
            if (password.length() < 8) {
                errors.put("password", "Password must be at least 8 characters long");
            }
            if (!password.matches(".*[A-Z].*")) {
                errors.put("password", "Password must contain at least one uppercase letter");
            }
            if (!password.matches(".*[a-z].*")) {
                errors.put("password", "Password must contain at least one lowercase letter");
            }
            if (!password.matches(".*\\d.*")) {
                errors.put("password", "Password must contain at least one digit");
            }
            if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
                errors.put("password", "Password must contain at least one special character: !@#$%^&*()_+-=[]{}:;'\"|,.<>/?");
            }
        }
        
        return errors;
    }
}