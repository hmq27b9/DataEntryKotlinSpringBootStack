package com.dataentrykotlinspringbootstack.backend.models

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository

@Entity
@Table(name = "entries")
class Entry(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User
)

interface EntryRepository : JpaRepository<Entry, Long> {
    fun findByUser(user: User): List<Entry>
}