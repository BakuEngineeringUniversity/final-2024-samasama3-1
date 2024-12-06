package com.example.petpal.services

import com.example.petpal.entites.UserEntity
import com.example.petpal.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getAllUsers(): List<UserEntity> {
        return userRepository.findAll()
    }

    fun getUserById(userId: Long): UserEntity {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with ID $userId not found") }

        // Access pets explicitly to ensure they are fetched
        user.pets.size // This triggers the loading if pets are lazy loaded
        return user
    }

    fun updateUser(userId: Long, updatedUser: UserEntity): UserEntity {
        val existingUser = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with ID $userId not found") }

        existingUser.firstName = updatedUser.firstName
        existingUser.lastName = updatedUser.lastName
        existingUser.email = updatedUser.email
        existingUser.phoneNumber = updatedUser.phoneNumber
        existingUser.address = updatedUser.address

        return userRepository.save(existingUser)
    }

    fun deleteUser(userId: Long) {
        val existingUser = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with ID $userId not found") }
        userRepository.delete(existingUser)
    }
}
