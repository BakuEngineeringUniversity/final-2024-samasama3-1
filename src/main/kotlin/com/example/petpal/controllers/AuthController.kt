package com.example.petpal.controllers

import com.example.petpal.common.ApiResponse
import com.example.petpal.dtos.LoginUserDto
import com.example.petpal.dtos.RegisterAdminDto
import com.example.petpal.dtos.RegisterUserDto
import com.example.petpal.services.AuthService
import com.example.petpal.exceptions.EmailOrPasswordWrongException
import com.example.petpal.exceptions.UserNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    private val logger: Logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/register")
    fun registerUser(@RequestBody registerUserDto: RegisterUserDto): ResponseEntity<ApiResponse<String>> {
        logger.info("Attempting to register user with email: ${registerUserDto.email}")
        return try {
            val message = authService.registerUser(registerUserDto)
            logger.info("User registered successfully with email: ${registerUserDto.email}")
            ResponseEntity.ok(ApiResponse("success", message, "User registered successfully"))
        } catch (e: Exception) {
            logger.error("Error registering user with email: ${registerUserDto.email}", e)
            throw e
        }
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody loginUserDto: LoginUserDto): ResponseEntity<ApiResponse<Map<String, Any>>> {
        logger.info("Attempting login for email: ${loginUserDto.email}")
        return try {
            val tokenWithRole = authService.loginUser(loginUserDto)
            val response = mapOf(
                "token" to tokenWithRole,
                "message" to "User logged in successfully"
            )
            logger.info("User logged in successfully with email: ${loginUserDto.email}")
            ResponseEntity.ok(ApiResponse("success", "User logged in successfully", response))
        } catch (e: UserNotFoundException) {
            logger.error("Login failed for email: ${loginUserDto.email}. User not found.", e)
            throw e
        } catch (e: EmailOrPasswordWrongException) {
            logger.error("Invalid login attempt for email: ${loginUserDto.email}. Invalid credentials.", e)
            throw e
        } catch (e: Exception) {
            logger.error("Unexpected error during login for email: ${loginUserDto.email}", e)
            throw e
        }
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @PostMapping("/register-admin")
    fun registerAdmin(@Valid @RequestBody registerAdminDto: RegisterAdminDto): ResponseEntity<ApiResponse<String>> {
        logger.info("Attempting to register new admin with email: ${registerAdminDto.email}")
        return try {
            val message = authService.registerAdmin(registerAdminDto)
            logger.info("Admin registered successfully with email: ${registerAdminDto.email}")
            ResponseEntity.ok(ApiResponse("success", message, "Admin registered successfully"))
        } catch (e: Exception) {
            logger.error("Error registering admin with email: ${registerAdminDto.email}", e)
            throw e
        }
    }
}
