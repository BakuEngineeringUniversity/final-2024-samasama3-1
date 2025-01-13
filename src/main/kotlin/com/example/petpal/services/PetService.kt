package com.example.petpal.services

import com.example.petpal.dtos.PetCreateDto
import com.example.petpal.dtos.PetUpdateDto
import com.example.petpal.entities.PetEntity
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
        return petRepository.findByUserId(userId).also {
            logger.info("Found ${it.size} pets for user ID: $userId")
        }
    }

    fun createPet(userId: Long, petCreateDto: PetCreateDto): PetEntity {
        logger.info("Creating a new pet for user ID: $userId")
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with ID $userId not found") }

        val newPet = PetEntity(
            name = petCreateDto.name,
            type = petCreateDto.type,
            sex = petCreateDto.sex,
            age = petCreateDto.age,
            user = user
        )
        logger.info("Pet created: ${newPet.name} for user ID: $userId")
        return petRepository.save(newPet)
    }

    fun updatePet(petId: Long, petUpdateDto: PetUpdateDto, userId: Long): PetEntity {
        val existingPet = petRepository.findById(petId)
            .orElseThrow { IllegalArgumentException("Pet with ID $petId not found") }

        if (existingPet.user?.id != userId) {
            throw IllegalArgumentException("You are not the owner of this pet")
        }

        petUpdateDto.name?.let { existingPet.name = it }
        petUpdateDto.type?.let { existingPet.type = it }
        petUpdateDto.sex.let { existingPet.sex = it }
        petUpdateDto.age?.let { existingPet.age = it }

        return petRepository.save(existingPet)
    }

    fun deletePet(petId: Long, userId: Long) {
        logger.info("Deleting pet with ID: $petId")
        val existingPet = petRepository.findById(petId)
            .orElseThrow { IllegalArgumentException("Pet with ID $petId not found") }

        if (existingPet.user?.id != userId) {
            throw IllegalArgumentException("You are not the owner of this pet")
        }

        petRepository.delete(existingPet)
        logger.info("Pet deleted with ID: $petId")
    }
}
