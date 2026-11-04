package com.dataentrykotlinspringbootstack.backend.controllers

import com.dataentrykotlinspringbootstack.backend.models.Entry
import com.dataentrykotlinspringbootstack.backend.models.EntryRepository
import com.dataentrykotlinspringbootstack.backend.models.User
import com.dataentrykotlinspringbootstack.backend.models.UserRepository
import jakarta.validation.constraints.NotBlank
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

data class EntryRequest(
    @field:NotBlank val content: String
)

data class EntryResponse(
    val id: Long,
    val content: String
)

@Service
class EntryController(
    private val entryRepository: EntryRepository,
    private val userRepository: UserRepository
) {

    fun createEntry(request: EntryRequest): EntryResponse {
        val user = currentUser()
        val saved = entryRepository.save(Entry(content = request.content, user = user))
        return EntryResponse(saved.id!!, saved.content)
    }

    fun listEntries(): List<EntryResponse> {
        val user = currentUser()
        return entryRepository.findByUser(user).map { EntryResponse(it.id!!, it.content) }
    }

    private fun currentUser(): User {
        val email = SecurityContextHolder.getContext().authentication?.name
            ?: throw IllegalStateException("No authenticated user")
        return userRepository.findByEmail(email)
            .orElseThrow { IllegalStateException("Authenticated user not found") }
    }
}