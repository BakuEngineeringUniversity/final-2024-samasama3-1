package com.example.petpal.services

import com.example.petpal.entities.UserEntity
import com.example.petpal.repositories.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user: UserEntity = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found with email: $email")
        return User(
            user.email,
            user.password,
            emptyList()
        )
    }
}
