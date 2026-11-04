package com.dataentrykotlinspringbootstack.frontend.network

import com.dataentrykotlinspringbootstack.frontend.models.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): TokenResponse

    @GET("entries")
    suspend fun listEntries(): List<EntryResponse>

    @POST("entries")
    suspend fun createEntry(@Body request: EntryRequest): EntryResponse
}