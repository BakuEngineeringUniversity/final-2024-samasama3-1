package com.example.petpal.dtos


import jakarta.validation.constraints.*

data class RegisterAdminDto(
    /* @field:Email(message = "Email must be valid") */
    val email: String,

    /* @field:NotBlank(message = "First name is required") */
    /* @field:Size(max = 50, message = "First name cannot exceed 50 characters") */
    val firstName: String,

    /* @field:NotBlank(message = "Surname is required") */
    /* @field:Size(max = 50, message = "Surname cannot exceed 50 characters") */
    val surname: String,

    /* @field:NotBlank(message = "Password is required") */
    /* @field:Size(min = 8, message = "Password must be at least 8 characters long") */
    /* @field:Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
        message = "Password must contain at least one letter and one number"
    ) */
    val password: String,

    // These fields are optional for admins. You can remove them if not needed.
    /* @field:Pattern(
        regexp = "^[0-9]{10,15}$",
        message = "Phone number must contain 10 to 15 digits"
    ) */
    val phoneNumber: String,

    /* @field:NotBlank(message = "Address is required") */
    /* @field:Size(max = 100, message = "Address cannot exceed 100 characters") */
    val address: String
)
