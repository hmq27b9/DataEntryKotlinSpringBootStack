package com.dataentrykotlinspringbootstack.backend.services

import com.dataentrykotlinspringbootstack.backend.dto.EntryRequest
import com.dataentrykotlinspringbootstack.backend.dto.EntryResponse
import com.dataentrykotlinspringbootstack.backend.models.Entry
import com.dataentrykotlinspringbootstack.backend.repositories.EntryRepository
import com.dataentrykotlinspringbootstack.backend.repositories.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class EntryService(
    private val entryRepository: EntryRepository,
    private val userRepository: UserRepository
) {

    fun createEntry(request: EntryRequest): EntryResponse {
        val email = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(email)
            .orElseThrow { IllegalStateException("Authenticated user not found") }

        val entry = Entry(content = request.content, user = user)
        val saved = entryRepository.save(entry)

        return EntryResponse(saved.id!!, saved.content)
    }

    fun listEntries(): List<EntryResponse> {
        val email = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(email)
            .orElseThrow { IllegalStateException("Authenticated user not found") }

        return entryRepository.findByUser(user).map { EntryResponse(it.id!!, it.content) }
    }
}