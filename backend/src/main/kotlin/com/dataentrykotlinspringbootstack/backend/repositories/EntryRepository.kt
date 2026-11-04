package com.dataentrykotlinspringbootstack.backend.repositories

import com.dataentrykotlinspringbootstack.backend.models.Entry
import com.dataentrykotlinspringbootstack.backend.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface EntryRepository : JpaRepository<Entry, Long> {
    fun findByUser(user: User): List<Entry>
}