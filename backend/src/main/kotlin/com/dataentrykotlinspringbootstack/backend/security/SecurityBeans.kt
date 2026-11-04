package com.dataentrykotlinspringbootstack.backend.security

import com.dataentrykotlinspringbootstack.backend.repositories.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SecurityBeans(private val userRepository: UserRepository) {

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { identifier ->
            val user = userRepository.findByEmail(identifier)
                .or { userRepository.findByUsername(identifier) }
                .orElseThrow { UsernameNotFoundException("User not found: $identifier") }

            org.springframework.security.core.userdetails.User(
                user.email,              // canonical principal = email
                user.passwordHash,
                emptyList()
            )
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}