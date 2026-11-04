package com.dataentrykotlinstack.backend.controllers

import com.dataentrykotlinstack.backend.dto.LoginRequest
import com.dataentrykotlinstack.backend.dto.RegisterRequest
import com.dataentrykotlinstack.backend.dto.TokenResponse
import com.dataentrykotlinstack.backend.services.AuthService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<TokenResponse> {
        val token = authService.register(request)
        return ResponseEntity.ok(TokenResponse(token))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<TokenResponse> {
        val token = authService.login(request)
        return ResponseEntity.ok(TokenResponse(token))
    }
}