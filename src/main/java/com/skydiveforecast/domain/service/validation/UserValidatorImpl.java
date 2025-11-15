package com.skydiveforecast.domain.service.validation;

import com.skydiveforecast.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserValidatorImpl implements UserValidator {

    private final UserRepositoryPort userRepository;

    @Override
    public Map<String, String> validateEmail(String email, boolean checkExistence) {
        Map<String, String> errors = new HashMap<>();
        
        if (email == null || email.isBlank()) {
            errors.put("email", "Email is required");
        } else if (checkExistence && userRepository.existsByEmail(email)) {
            errors.put("email", "Email already in use");
        }
        
        return errors;
    }

    @Override
    public Map<String, String> validateName(String firstName, String lastName) {
        Map<String, String> errors = new HashMap<>();
        
        if (firstName == null || firstName.isBlank()) {
            errors.put("firstName", "First name is required");
        } else if (firstName.length() > 50) {
            errors.put("firstName", "First name cannot exceed 50 characters");
        }

        if (lastName == null || lastName.isBlank()) {
            errors.put("lastName", "Last name is required");
        } else if (lastName.length() > 50) {
            errors.put("lastName", "Last name cannot exceed 50 characters");
        }
        
        return errors;
    }

    @Override
    public Map<String, String> validatePhoneNumber(String phoneNumber) {
        Map<String, String> errors = new HashMap<>();
        
        if (phoneNumber != null && !phoneNumber.matches("^\\+?[0-9]{10,15}$")) {
            errors.put("phoneNumber", "Phone number must be valid");
        }
        
        return errors;
    }
}
