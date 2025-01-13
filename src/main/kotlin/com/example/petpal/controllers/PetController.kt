package com.example.petpal.controllers

import com.example.petpal.common.ApiResponse
import com.example.petpal.dtos.PetCreateDto
import com.example.petpal.dtos.PetUpdateDto
import com.example.petpal.entities.PetEntity
import com.example.petpal.services.PetService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pets")
class PetController(private val petService: PetService) {

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun getPetsByUser(@PathVariable userId: Long): ResponseEntity<ApiResponse<List<PetEntity>>> {
        val pets = petService.getPetsByUserId(userId)
        return ResponseEntity.ok(ApiResponse("success", pets, "Pets retrieved successfully"))
    }

    @PostMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    fun createPet(
        @PathVariable userId: Long,
        @RequestBody petCreateDto: PetCreateDto
    ): ResponseEntity<ApiResponse<PetEntity>> {
        val newPet = petService.createPet(userId, petCreateDto)
        return ResponseEntity.ok(ApiResponse("success", newPet, "Pet created successfully"))
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    fun updatePet(
        @PathVariable id: Long,
        @RequestBody petUpdateDto: PetUpdateDto
    ): ResponseEntity<ApiResponse<PetEntity>> {
        val updatedPet = petService.updatePet(id, petUpdateDto)
        return ResponseEntity.ok(ApiResponse("success", updatedPet, "Pet updated successfully"))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    fun deletePet(@PathVariable id: Long): ResponseEntity<ApiResponse<String>> {
        petService.deletePet(id)
        return ResponseEntity.ok(ApiResponse("success", null, "Pet with ID $id deleted successfully"))
    }
}
