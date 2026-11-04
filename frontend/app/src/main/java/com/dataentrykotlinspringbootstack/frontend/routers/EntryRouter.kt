package com.dataentrykotlinspringbootstack.frontend.routers

import com.dataentrykotlinspringbootstack.frontend.controllers.AuthInterceptor
import com.dataentrykotlinspringbootstack.frontend.models.EntryRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object EntryRouter {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val json = Json { ignoreUnknownKeys = true }

    fun create(interceptor: AuthInterceptor): EntryRepository {
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(EntryRepository::class.java)
    }
}