package com.dataentrykotlinspringbootstack.backend.routers

import com.dataentrykotlinspringbootstack.backend.controllers.AuthController
import com.dataentrykotlinspringbootstack.backend.controllers.LoginRequest
import com.dataentrykotlinspringbootstack.backend.controllers.TokenResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthRouter(private val authController: AuthController) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<TokenResponse> {
        val token = authController.login(request)
        return ResponseEntity.ok(TokenResponse(token))
    }
}