package org.backend.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.backend.domains.user.Roles;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    @NotEmpty(message = "firstname is required")
    @NotBlank(message = "firstname is required")
    private String firstName;
    @NotEmpty(message = "lastname is required")
    @NotBlank(message = "lastname is required")
    private String lastName;
    @Email(message = "the email is not correctly formatted")
    @NotEmpty(message = "email is required")
    @NotBlank(message = "email  is required")
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotEmpty(message = "date of birth is required")
    @NotBlank(message = "date of birth required")
    private LocalDate birthDay;
    @NotEmpty(message = "password is required")
    @NotBlank(message = "password is required")
    @Size(min = 8, message = "the password should be minimum 8 charachters" )
    private String password;
    @NonNull
    Roles role;

}
