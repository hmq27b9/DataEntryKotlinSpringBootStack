package com.dataentrykotlinspringbootstack.frontend.models

import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

@Serializable
data class EntryRequest(val content: String)

@Serializable
data class EntryResponse(val id: Long, val content: String)

interface EntryRepository {
    @GET("entries")
    suspend fun listEntries(): List<EntryResponse>

    @POST("entries")
    suspend fun createEntry(@Body request: EntryRequest): EntryResponse
}