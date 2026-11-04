package com.dataentrykotlinspringbootstack.backend.dto

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank val identifier: String,
    @field:NotBlank val password: String
)

data class TokenResponse(val token: String)