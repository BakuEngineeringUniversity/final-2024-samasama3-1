package com.example.petpal.controllers

import com.example.petpal.dtos.PetCreateDto
import com.example.petpal.dtos.PetUpdateDto
import com.example.petpal.entities.PetEntity
import com.example.petpal.services.PetService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pets")
class PetController(private val petService: PetService) {

    @GetMapping("/user/{userId}")
    fun getPetsByUser(@PathVariable userId: Long): ResponseEntity<List<PetEntity>> {
        val pets = petService.getPetsByUserId(userId)
        return ResponseEntity.ok(pets)
    }

    @PostMapping("/user/{userId}")
    fun createPet(@PathVariable userId: Long, @RequestBody petCreateDto: PetCreateDto): ResponseEntity<PetEntity> {
        val newPet = petService.createPet(userId, petCreateDto)
        return ResponseEntity.ok(newPet)
    }

    @PutMapping("/{id}")
    fun updatePet(@PathVariable id: Long, @RequestBody petUpdateDto: PetUpdateDto): ResponseEntity<PetEntity> {
        val updatedPet = petService.updatePet(id, petUpdateDto)
        return ResponseEntity.ok(updatedPet)
    }

    @DeleteMapping("/{id}")
    fun deletePet(@PathVariable id: Long): ResponseEntity<String> {
        petService.deletePet(id)
        return ResponseEntity.ok("Pet with ID $id deleted successfully.")
    }
}
