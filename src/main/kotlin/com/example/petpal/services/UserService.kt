package com.example.petpal.services

import com.example.petpal.entities.UserEntity
import com.example.petpal.repositories.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(pageable: Pageable): Page<UserEntity> = userRepository.findAll(pageable)

    fun getUserById(userId: Long): UserEntity {
        return userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with ID $userId not found") }
    }
}
