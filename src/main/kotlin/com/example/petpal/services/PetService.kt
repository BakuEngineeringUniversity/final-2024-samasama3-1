package com.example.petpal.services

import com.example.petpal.dtos.PetCreateDto
import com.example.petpal.dtos.PetUpdateDto
import com.example.petpal.entities.PetEntity
import com.example.petpal.enums.UserRoles
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.example.petpal.repositories.PetRepository
import com.example.petpal.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class PetService(
    private val petRepository: PetRepository,
    private val userRepository: UserRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(PetService::class.java)

    fun getPetsByUserId(userId: Long): List<PetEntity> {
        logger.info("Fetching pets for user with ID: $userId")
        return petRepository.findByUserId(userId)
    }

    fun createPet(userId: Long, petCreateDto: PetCreateDto): PetEntity {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with ID $userId not found") }

        val newPet = PetEntity(
            name = petCreateDto.name,
            type = petCreateDto.type,
            sex = petCreateDto.sex,
            age = petCreateDto.age,
            user = user
        )

        logger.info("Creating a new pet for user with ID: $userId")
        return petRepository.save(newPet)
    }

    fun updatePet(petId: Long, petUpdateDto: PetUpdateDto, userId: Long): PetEntity {
        val existingPet = petRepository.findById(petId)
            .orElseThrow { IllegalArgumentException("Pet with ID $petId not found") }

        if (existingPet.user?.id != userId && !isAdmin(userId)) {
            throw IllegalArgumentException("You are not the owner of this pet")
        }

        // Update pet fields
        petUpdateDto.name?.let { existingPet.name = it }
        petUpdateDto.type?.let { existingPet.type = it }
        petUpdateDto.sex.let { existingPet.sex = it }
        petUpdateDto.age?.let { existingPet.age = it }

        logger.info("Updating pet with ID: $petId")
        return petRepository.save(existingPet)
    }

    fun deletePet(petId: Long, userId: Long) {
        val existingPet = petRepository.findById(petId)
            .orElseThrow { IllegalArgumentException("Pet with ID $petId not found") }

        if (existingPet.user?.id != userId && !isAdmin(userId)) {
            throw IllegalArgumentException("You are not the owner of this pet")
        }

        logger.info("Deleting pet with ID: $petId")
        petRepository.delete(existingPet)
    }

    private fun isAdmin(userId: Long): Boolean {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException("User not found") }
        return user.role == UserRoles.ADMIN
    }

}