package com.dataentrykotlinspringbootstack.backend.dto

import jakarta.validation.constraints.NotBlank

data class EntryRequest(
    @field:NotBlank val content: String
)

data class EntryResponse(
    val id: Long,
    val content: String
)