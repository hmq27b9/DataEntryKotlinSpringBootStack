package com.dataentrykotlinspringbootstack.backend.services

import com.dataentrykotlinspringbootstack.backend.dto.LoginRequest
import com.dataentrykotlinspringbootstack.backend.repositories.UserRepository
import com.dataentrykotlinspringbootstack.backend.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    fun login(request: LoginRequest): String {
        // Authenticate with whatever the user typed — DaoAuthenticationProvider
        // delegates to userDetailsService, which resolves email OR username.
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.identifier, request.password)
        )

        // The principal (canonical identity) is always the email, so the JWT
        // subject is stable regardless of which identifier was used to log in.
        val user = userRepository.findByEmail(request.identifier)
            .or { userRepository.findByUsername(request.identifier) }
            .orElseThrow { UsernameNotFoundException("User not found: ${request.identifier}") }

        return jwtService.generateToken(user.email)
    }
}