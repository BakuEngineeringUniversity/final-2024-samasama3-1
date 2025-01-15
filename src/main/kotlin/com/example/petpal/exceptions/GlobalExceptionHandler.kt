package com.example.petpal.exceptions

import com.example.petpal.common.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception): ResponseEntity<ApiResponse<String>> {
        return ResponseEntity(
            ApiResponse(
                status = "error",
                message = e.localizedMessage,
                data = null,
                errorCode = "GENERIC_ERROR"
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(e: UserNotFoundException): ResponseEntity<ApiResponse<String>> {
        return ResponseEntity(
            ApiResponse(
                status = "error",
                message = e.message ?: "User not found",
                data = null,
                errorCode = "USER_NOT_FOUND"
            ),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(PetNotFoundException::class)
    fun handlePetNotFound(e: PetNotFoundException): ResponseEntity<ApiResponse<String>> {
        return ResponseEntity(
            ApiResponse(
                status = "error",
                message = e.message ?: "Pet not found",
                data = null,
                errorCode = "PET_NOT_FOUND"
            ),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(InvalidPetOwnershipException::class)
    fun handleInvalidPetOwnership(e: InvalidPetOwnershipException): ResponseEntity<ApiResponse<String>> {
        return ResponseEntity(
            ApiResponse(
                status = "error",
                message = e.message ?: "You do not have permission to access this pet",
                data = null,
                errorCode = "INVALID_PET_OWNERSHIP"
            ),
            HttpStatus.FORBIDDEN
        )
    }

    @ExceptionHandler(EmailOrPasswordWrongException::class)
    fun handleInvalidLogin(e: EmailOrPasswordWrongException): ResponseEntity<ApiResponse<String>> {
        return ResponseEntity(
            ApiResponse(
                status = "error",
                message = e.message ?: "Invalid email or password",
                data = null,
                errorCode = "INVALID_CREDENTIALS"
            ),
            HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedAccess(e: UnauthorizedException): ResponseEntity<ApiResponse<String>> {
        return ResponseEntity(
            ApiResponse(
                status = "error",
                message = e.message ?: "You are not authorized to perform this action",
                data = null,
                errorCode = "UNAUTHORIZED"
            ),
            HttpStatus.UNAUTHORIZED
        )
    }
}

