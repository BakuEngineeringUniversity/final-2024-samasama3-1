package com.example.petpal.dtos

import jakarta.validation.constraints.*

data class LoginUserDto(
    /*@field:Email(message = "Email must be valid")
    @field:NotBlank(message = "Email is required")
     */
    val email: String,

    /*@field:NotBlank(message = "Password is required")
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    @field:Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
        message = "Password must contain at least one letter and one number"
    )*/
    val password: String
)
