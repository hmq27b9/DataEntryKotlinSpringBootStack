package com.dataentrykotlinspringbootstack.frontend.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val identifier: String, val password: String)

@Serializable
data class TokenResponse(val token: String)

@Serializable
data class EntryRequest(val content: String)

@Serializable
data class EntryResponse(val id: Long, val content: String)