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
        return UserDetailsService { email ->
            val user = userRepository.findByEmail(email)
                .orElseThrow { UsernameNotFoundException("User not found: $email") }
            org.springframework.security.core.userdetails.User(
                user.email,
                user.passwordHash,
                emptyList()
            )
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}