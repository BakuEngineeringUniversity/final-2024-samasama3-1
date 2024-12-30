package com.example.petpal.services

import com.example.petpal.entities.UserEntity
import com.example.petpal.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<UserEntity> = userRepository.findAll()

    fun getUserById(userId: Long): UserEntity {
        return userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with ID $userId not found") }
    }
}
