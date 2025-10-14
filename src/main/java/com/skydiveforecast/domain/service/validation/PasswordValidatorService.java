package com.skydiveforecast.domain.service.validation;

import java.util.Map;

public interface PasswordValidatorService {

    Map<String, String> validate(String password);
}
