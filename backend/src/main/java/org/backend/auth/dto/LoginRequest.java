package org.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Email(message = "the email is not correctly formatted")
    @NotEmpty(message = "email is required")
    @NotBlank(message = "email  is required")
    private String email;
    @NotEmpty(message = "password is required")
    @NotBlank(message = "password is required")
    @Size(min = 8, message = "the password should be minimum 8 charachters" )
    private String password;
}
