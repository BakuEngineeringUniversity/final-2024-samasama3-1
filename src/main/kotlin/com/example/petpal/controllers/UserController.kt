package com.example.petpal.controllers

import com.example.petpal.entites.UserEntity
import com.example.petpal.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getAllUsers(): List<UserEntity> {
        return userService.getAllUsers()
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): UserEntity {
        return userService.getUserById(id)
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody updatedUser: UserEntity
    ): ResponseEntity<UserEntity> {
        val user = userService.updateUser(id, updatedUser)
        return ResponseEntity.ok(user)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<String> {
        userService.deleteUser(id)
        return ResponseEntity.ok("User with ID $id deleted successfully.")
    }
}
