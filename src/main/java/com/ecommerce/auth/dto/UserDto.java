package com.ecommerce.auth.dto;

public record UserDto(
        Long id,
        String email,
        String fullName,
        String phoneNumber,
        String role
) {}
