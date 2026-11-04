package com.dataentrykotlinstack.backend.repositories

import com.dataentrykotlinstack.backend.models.Entry
import com.dataentrykotlinstack.backend.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface EntryRepository : JpaRepository<Entry, Long> {
    fun findByUser(user: User): List<Entry>
}