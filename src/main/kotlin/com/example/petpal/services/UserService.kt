package com.example.petpal.services

import com.example.petpal.entities.UserEntity
import com.example.petpal.exceptions.UserNotFoundException
import com.example.petpal.repositories.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    private val logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun getAllUsers(pageable: Pageable): Page<UserEntity> {
        logger.info("Fetching all users with pagination: page=${pageable.pageNumber}, size=${pageable.pageSize}")
        val users = userRepository.findAll(pageable)
        logger.info("Fetched ${users.totalElements} users")
        return users
    }

    fun getUserById(userId: Long): UserEntity {
        logger.info("Fetching user with ID: $userId")
        return userRepository.findById(userId)
            .orElseThrow {
                logger.error("User with ID $userId not found")
                UserNotFoundException("User with ID $userId not found")
            }.also {
                logger.info("Successfully fetched user with ID: $userId")
            }
    }
}
