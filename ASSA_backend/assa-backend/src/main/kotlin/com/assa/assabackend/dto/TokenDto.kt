package com.assa.assabackend.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer",
    val expiresIn: Long
)

data class TokenRefreshRequest(
    @field:NotBlank(message = "Refresh token is required")
    val refreshToken: String
)

data class LoginRequest(
    @field:Email(message = "Valid email is required")
    @field:NotBlank(message = "Email is required")
    val email: String,

    @field:NotBlank(message = "Password is required")
    val password: String
)
