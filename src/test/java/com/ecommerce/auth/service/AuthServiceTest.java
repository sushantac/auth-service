package com.ecommerce.auth.service;

import com.ecommerce.auth.dto.*;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.event.UserEventPublisher;
import com.ecommerce.auth.exception.ApiException;
import com.ecommerce.auth.repository.RefreshTokenRepository;
import com.ecommerce.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private RefreshTokenRepository refreshTokenRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;
    @Mock private UserEventPublisher eventPublisher;

    @InjectMocks private AuthService authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("test@example.com", "encoded-pass", "Test User", "1234567890");
        testUser.setId(1L);
    }

    @Test
    void register_duplicateEmail_throws409() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        ApiException ex = assertThrows(ApiException.class,
                () -> authService.register(new RegisterRequest("test@example.com", "password123", "Test User", null)));
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    }

    @Test
    void login_wrongPassword_throws401() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrong", "encoded-pass")).thenReturn(false);

        ApiException ex = assertThrows(ApiException.class,
                () -> authService.login(new LoginRequest("test@example.com", "wrong")));
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());
    }

    @Test
    void refresh_invalidToken_throws401() {
        when(jwtService.parseClaims("bad-token")).thenThrow(new RuntimeException("invalid"));
        assertThrows(Exception.class,
                () -> authService.refresh(new RefreshRequest("bad-token")));
    }
}
