package com.dataentrykotlinspringbootstack.backend.controllers

import com.dataentrykotlinspringbootstack.backend.dto.LoginRequest
import com.dataentrykotlinspringbootstack.backend.dto.TokenResponse
import com.dataentrykotlinspringbootstack.backend.services.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<TokenResponse> {
        val token = authService.login(request)
        return ResponseEntity.ok(TokenResponse(token))
    }
}