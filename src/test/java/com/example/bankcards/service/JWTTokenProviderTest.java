package com.example.bankcards.service;

import com.example.bankcards.entity.Role;
import com.example.bankcards.security.JWTTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JWTTokenProviderTest {

    private JWTTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {

        jwtTokenProvider = new JWTTokenProvider();

        ReflectionTestUtils.setField(jwtTokenProvider, "secret", "dGhpcyBpcyBhIHZlcnkgbG9uZyBzZWNyZXQga2V5IGZvciBqd3Q=");
        ReflectionTestUtils.setField(jwtTokenProvider, "expiration", 86400000L);
    }


    @Test
    public void generationAndValidation_success() {

        String token = jwtTokenProvider.generateToken("testUser", Role.USER.name());
        boolean isValid = jwtTokenProvider.validateToken(token);

        assertTrue(isValid);
    }


    @Test
    public void validation_invalid() {

        String token = "token";
        boolean isValid = jwtTokenProvider.validateToken(token);

        assertFalse(isValid);
    }


    @Test
    public void getUsername_success() {

        String token = jwtTokenProvider.generateToken("testUser", Role.USER.name());
        String username = jwtTokenProvider.getUsername(token);

        assertEquals("testUser", username);
    }
}
