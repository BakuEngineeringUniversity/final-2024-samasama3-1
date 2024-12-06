package com.example.petpal.controllers

import com.example.petpal.dtos.LoginUserDto
import com.example.petpal.dtos.RegisterUserDto
import com.example.petpal.services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody registerUserDto: RegisterUserDto): ResponseEntity<String> {
        val message = authService.registerUser(registerUserDto)
        return ResponseEntity.ok(message)
    }

//    @PostMapping("/login")
//    fun loginUser(@RequestBody loginUserDto: LoginUserDto): ResponseEntity<Map<String, String>> {
//        val token = authService.loginUser(loginUserDto)
//        return ResponseEntity.ok(mapOf("token" to token))
//    }
}