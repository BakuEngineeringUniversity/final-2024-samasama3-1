package com.example.petpal.controllers

import com.example.petpal.common.ApiResponse
import com.example.petpal.dtos.LoginUserDto
import com.example.petpal.dtos.RegisterUserDto
import com.example.petpal.services.AuthService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    private val logger: Logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/register")
    fun registerUser(@RequestBody registerUserDto: RegisterUserDto): ResponseEntity<ApiResponse<String>> {
        logger.info("Registering user with email: ${registerUserDto.email}")
        val message = authService.registerUser(registerUserDto)
        logger.info("User registered successfully with email: ${registerUserDto.email}") // Log success
        return ResponseEntity.ok(ApiResponse("success", message, "User registered successfully"))
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody loginUserDto: LoginUserDto): ResponseEntity<ApiResponse<Map<String, Any>>> {
        logger.info("User login attempt with email: ${loginUserDto.email}")
        val tokenWithRole = authService.loginUser(loginUserDto)
        val response = mapOf(
            "token" to tokenWithRole,
            "message" to "User logged in successfully"
        )
        logger.info("User logged in successfully with email: ${loginUserDto.email}") // Log success
        return ResponseEntity.ok(ApiResponse("success", response, "User logged in successfully"))
    }
}
