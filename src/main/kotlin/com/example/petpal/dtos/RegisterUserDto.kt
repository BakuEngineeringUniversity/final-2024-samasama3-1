package com.example.petpal.dtos

import com.example.petpal.enums.Sex
import com.example.petpal.enums.UserRoles

data class RegisterUserDto(
    /*@field:Email(message = "Email must be valid")*/
    val email: String,

    /*@field:NotBlank(message = "First name is required")
    @field:Size(max = 50, message = "First name cannot exceed 50 characters")*/
    val firstName: String,

    /*@field:NotBlank(message = "Surname is required")
    @field:Size(max = 50, message = "Surname cannot exceed 50 characters")*/
    val surname: String,

    /*@field:Size(min = 8, message = "Password must be at least 8 characters long")
    @field:Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
        message = "Password must contain at least one letter and one number"
    )*/
    val password: String,

    /*@field:Pattern(
        regexp = "^[0-9]{10,15}$",
        message = "Phone number must contain 10 to 15 digits"
    )*/
    val phoneNumber: String,

    /*@field:NotBlank(message = "Address is required")
    @field:Size(max = 100, message = "Address cannot exceed 100 characters")*/
    val address: String,

    /*@field:NotBlank(message = "Pet name is required")
    @field:Size(max = 50, message = "Pet name cannot exceed 50 characters")*/
    val petName: String,

    /*@field:NotNull(message = "Pet sex is required")*/
    val petSex: Sex,

    /*@field:NotBlank(message = "Pet type is required")
    @field:Size(max = 30, message = "Pet type cannot exceed 30 characters")*/
    val petType: String,

    /*@field:Min(0, message = "Pet age must be at least 0")
    @field:Max(100, message = "Pet age must not exceed 100")*/
    val petAge: Int,

    val role: UserRoles = UserRoles.USER
)
