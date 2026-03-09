package com.example.bankcards.service;

import com.example.bankcards.entity.AppUser;
import com.example.bankcards.entity.Role;
import com.example.bankcards.exception.InvalidCredentialsException;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.JWTTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JWTTokenProvider  jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JWTTokenProvider jwtTokenProvider,  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(String username, String password) {

        if (username == null || username.isEmpty()) {
            throw new InvalidCredentialsException("Username cannot be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new InvalidCredentialsException("Password cannot be null or empty");
        }

        userRepository.save(AppUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .build());
    }

    public String login(String username, String password) {
        AppUser user = userRepository.findByUsername(username).orElseThrow(() ->
                new InvalidCredentialsException("Invalid username or password"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
        return jwtTokenProvider.generateToken(username, user.getRole().name());
    }

}
