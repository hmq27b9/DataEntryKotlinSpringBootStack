package com.dataentrykotlinspringbootstack.frontend.models

import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

@Serializable
data class LoginRequest(val identifier: String, val password: String)

@Serializable
data class TokenResponse(val token: String)

interface UserRepository {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): TokenResponse
}