package com.dataentrykotlinspringbootstack.backend.models

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["email"]),
        UniqueConstraint(columnNames = ["username"])
    ]
)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false, unique = true)
    val username: String,

    @Column(nullable = false)
    var passwordHash: String
)

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
    fun findByUsername(username: String): Optional<User>
}