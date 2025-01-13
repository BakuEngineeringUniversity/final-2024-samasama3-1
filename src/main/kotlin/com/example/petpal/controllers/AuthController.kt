package com.example.petpal.controllers

import com.example.petpal.common.ApiResponse
import com.example.petpal.dtos.LoginUserDto
import com.example.petpal.dtos.RegisterUserDto
import com.example.petpal.services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody registerUserDto: RegisterUserDto): ResponseEntity<ApiResponse<String>> {
        val message = authService.registerUser(registerUserDto)
        return ResponseEntity.ok(ApiResponse("success", message, "User registered successfully"))
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody loginUserDto: LoginUserDto): ResponseEntity<ApiResponse<Map<String, Any>>> {
        val tokenWithRole = authService.loginUser(loginUserDto)
        val response = mapOf(
            "token" to tokenWithRole,
            "message" to "User logged in successfully"
        )
        return ResponseEntity.ok(ApiResponse("success", response, "User logged in successfully"))
    }
}

