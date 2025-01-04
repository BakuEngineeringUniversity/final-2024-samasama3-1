package com.example.petpal.controllers

import com.example.petpal.entities.UserEntity
import com.example.petpal.services.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {
    @GetMapping
    fun getAllUsers(): List<UserEntity> = userService.getAllUsers()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): UserEntity = userService.getUserById(id)
}
