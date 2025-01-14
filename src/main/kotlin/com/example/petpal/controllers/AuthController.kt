package com.example.petpal.controllers

import com.example.petpal.common.ApiResponse
import com.example.petpal.dtos.LoginUserDto
import com.example.petpal.dtos.RegisterAdminDto
import com.example.petpal.dtos.RegisterUserDto
import com.example.petpal.services.AuthService
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    private val logger: Logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/register")
    fun registerUser(@RequestBody registerUserDto: RegisterUserDto): ResponseEntity<ApiResponse<String>> {
        logger.info("Registering user with email: ${registerUserDto.email}")
        val message = authService.registerUser(registerUserDto)
        logger.info("User registered successfully with email: ${registerUserDto.email}")
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
        logger.info("User logged in successfully with email: ${loginUserDto.email}")
        return ResponseEntity.ok(ApiResponse("success", response, "User logged in successfully"))
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register-admin")
    fun registerAdmin(@Valid @RequestBody registerAdminDto: RegisterAdminDto): ResponseEntity<ApiResponse<String>> {
        logger.info("Attempting to register new admin with email: ${registerAdminDto.email}")
        val message = authService.registerAdmin(registerAdminDto)
        logger.info("Admin registered successfully with email: ${registerAdminDto.email}")
        return ResponseEntity.ok(ApiResponse("success", message, "Admin registered successfully"))
    }
}
