package com.example.petpal.controllers

import com.example.petpal.common.ApiResponse
import com.example.petpal.entities.UserEntity
import com.example.petpal.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {
    @GetMapping
    fun getAllUsers(): ResponseEntity<ApiResponse<List<UserEntity>>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(ApiResponse("success", users, "All users retrieved successfully"))
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<ApiResponse<UserEntity>> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(ApiResponse("success", user, "User retrieved successfully"))
    }
}
