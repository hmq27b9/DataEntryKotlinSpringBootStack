package com.dataentrykotlinspringbootstack.backend.services

import com.dataentrykotlinspringbootstack.backend.dto.LoginRequest
import com.dataentrykotlinspringbootstack.backend.dto.RegisterRequest
import com.dataentrykotlinspringbootstack.backend.models.User
import com.dataentrykotlinspringbootstack.backend.repositories.UserRepository
import com.dataentrykotlinspringbootstack.backend.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    fun register(request: RegisterRequest): String {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already registered")
        }

        val user = User(
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password)
                ?: throw IllegalStateException("Password encoding failed")
        )
        userRepository.save(user)

        return jwtService.generateToken(user.email)
    }

    fun login(request: LoginRequest): String {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )
        return jwtService.generateToken(request.email)
    }
}