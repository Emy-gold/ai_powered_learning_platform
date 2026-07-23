package org.backend.auth.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.backend.auth.dto.RegistrationRequest;
import org.backend.domains.user.Roles;
import org.backend.domains.user.User;
import org.backend.modules.user.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    public User signup(RegistrationRequest request) throws IllegalAccessException{
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalAccessException("Email already exists");
        }
        Roles selectedRole = request.getRole();
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .birthDay(request.getBirthDay())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(true)
                .role(List.of(selectedRole))
                .build();
        return userRepository.save(user);
    }

}
