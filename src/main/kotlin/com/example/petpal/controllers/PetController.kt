package com.example.petpal.controllers

import com.example.petpal.common.ApiResponse
import com.example.petpal.config.CustomUserDetails
import com.example.petpal.dtos.PetCreateDto
import com.example.petpal.dtos.PetUpdateDto
import com.example.petpal.entities.PetEntity
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
        val pets = petService.getPetsByUserId(userId)
        return ResponseEntity.ok(ApiResponse("success", pets, "Pets retrieved successfully"))
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('USER')")
    fun createPet(@RequestBody petCreateDto: PetCreateDto): ResponseEntity<ApiResponse<PetEntity>> {
        val userId = getCurrentUserId()
        logger.info("User with ID $userId is creating a new pet: ${petCreateDto.name}")
        val newPet = petService.createPet(userId, petCreateDto)
        return ResponseEntity.ok(ApiResponse("success", newPet, "Pet created successfully"))
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun updatePet(@PathVariable id: Long, @RequestBody petUpdateDto: PetUpdateDto): ResponseEntity<ApiResponse<PetEntity>> {
        val currentUserId = getCurrentUserId()
        logger.info("User with ID $currentUserId is updating pet with ID $id")
        val updatedPet = petService.updatePet(id, petUpdateDto, currentUserId)
        return ResponseEntity.ok(ApiResponse("success", updatedPet, "Pet updated successfully"))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun deletePet(@PathVariable id: Long): ResponseEntity<ApiResponse<String>> {
        val currentUserId = getCurrentUserId()
        logger.info("User with ID $currentUserId is deleting pet with ID $id")
        petService.deletePet(id, currentUserId)
        return ResponseEntity.ok(ApiResponse("success", null, "Pet with ID $id deleted successfully"))
    }
}
