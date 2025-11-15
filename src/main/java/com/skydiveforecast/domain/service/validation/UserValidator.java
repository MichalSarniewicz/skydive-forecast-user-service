package com.skydiveforecast.domain.service.validation;

import java.util.Map;

public interface UserValidator {

    Map<String, String> validateEmail(String email, boolean checkExistence);

    Map<String, String> validateName(String firstName, String lastName);

    Map<String, String> validatePhoneNumber(String phoneNumber);
}
