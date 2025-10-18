package com.skydiveforecast.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordChangeDto {

    @NotBlank(message = "Current password must not be blank.")
    private String currentPassword;

    @NotBlank(message = "New password must not be blank.")
    @Size(min = 12, message = "New password must be at least 12 characters long.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "New password must contain at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    private String newPassword;
}
