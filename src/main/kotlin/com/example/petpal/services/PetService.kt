package com.example.petpal.services

import com.example.petpal.dtos.PetCreateDto
import com.example.petpal.dtos.PetUpdateDto
import com.example.petpal.entities.PetEntity
import com.example.petpal.repositories.PetRepository
import com.example.petpal.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class PetService(
    private val petRepository: PetRepository,
    private val userRepository: UserRepository
) {

    fun getPetsByUserId(userId: Long): List<PetEntity> {
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
        return petRepository.save(newPet)
    }

    fun updatePet(petId: Long, petUpdateDto: PetUpdateDto): PetEntity {
        val existingPet = petRepository.findById(petId)
            .orElseThrow { IllegalArgumentException("Pet with ID $petId not found") }

        petUpdateDto.name?.let { existingPet.name = it }
        petUpdateDto.type?.let { existingPet.type = it }
        petUpdateDto.sex?.let { existingPet.sex = it }
        petUpdateDto.age?.let { existingPet.age = it }

        return petRepository.save(existingPet)
    }

    fun deletePet(petId: Long) {
        val existingPet = petRepository.findById(petId)
            .orElseThrow { IllegalArgumentException("Pet with ID $petId not found") }
        petRepository.delete(existingPet)
    }
}
