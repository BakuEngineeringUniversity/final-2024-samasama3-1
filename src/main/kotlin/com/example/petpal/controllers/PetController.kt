package com.example.petpal.controllers

import com.example.petpal.common.ApiResponse
import com.example.petpal.config.CustomUserDetails
import com.example.petpal.dtos.PetCreateDto
import com.example.petpal.dtos.PetUpdateDto
import com.example.petpal.entities.PetEntity
import com.example.petpal.exceptions.InvalidPetOwnershipException
import com.example.petpal.exceptions.PetNotFoundException
import com.example.petpal.services.PetService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pets")
class PetController(private val petService: PetService) {

    private val logger: Logger = LoggerFactory.getLogger(PetController::class.java)

    private fun getCurrentUserId(): Long {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as CustomUserDetails
        return userDetails.id
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun getPetsByUser(@PathVariable userId: Long): ResponseEntity<ApiResponse<List<PetEntity>>> {
        logger.info("Admin is fetching pets for user with ID: $userId")
        return try {
            val pets = petService.getPetsByUserId(userId)
            ResponseEntity.ok(ApiResponse("success", "Pets retrieved successfully", pets))
        } catch (e: PetNotFoundException) {
            logger.error("Error fetching pets for user ID: $userId", e)
            throw e
        }
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('USER')")
    fun createPet(@RequestBody petCreateDto: PetCreateDto): ResponseEntity<ApiResponse<PetEntity>> {
        val userId = getCurrentUserId()
        logger.info("User with ID $userId is creating a new pet: ${petCreateDto.name}")
        return try {
            val newPet = petService.createPet(userId, petCreateDto)
            ResponseEntity.ok(ApiResponse("success", "Pet created successfully", newPet))
        } catch (e: Exception) {
            logger.error("Error creating pet for user ID: $userId", e)
            throw e
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun updatePet(@PathVariable id: Long, @RequestBody petUpdateDto: PetUpdateDto): ResponseEntity<ApiResponse<PetEntity>> {
        val currentUserId = getCurrentUserId()
        logger.info("User with ID $currentUserId is updating pet with ID $id")
        return try {
            val updatedPet = petService.updatePet(id, petUpdateDto, currentUserId)
            ResponseEntity.ok(ApiResponse("success", "Pet updated successfully", updatedPet))
        } catch (e: InvalidPetOwnershipException) {
            logger.error("User with ID $currentUserId does not own pet with ID $id", e)
            throw e
        } catch (e: PetNotFoundException) {
            logger.error("Pet with ID $id not found", e)
            throw e
        } catch (e: Exception) {
            logger.error("Error updating pet with ID: $id", e)
            throw e
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun deletePet(@PathVariable id: Long): ResponseEntity<ApiResponse<String>> {
        val currentUserId = getCurrentUserId()
        logger.info("User with ID $currentUserId is deleting pet with ID $id")
        return try {
            petService.deletePet(id, currentUserId)
            ResponseEntity.ok(ApiResponse("success", "Pet with ID $id deleted successfully", null))
        } catch (e: InvalidPetOwnershipException) {
            logger.error("User with ID $currentUserId does not own pet with ID $id", e)
            throw e
        } catch (e: PetNotFoundException) {
            logger.error("Pet with ID $id not found", e)
            throw e
        }
    }
}
