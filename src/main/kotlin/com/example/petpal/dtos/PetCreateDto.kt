package com.example.petpal.dtos


import jakarta.validation.constraints.*
import com.example.petpal.enums.Sex
import org.springframework.web.multipart.MultipartFile

data class PetCreateDto(
    /*@field:NotBlank(message = "Pet name is required")
    @field:Size(max = 50, message = "Pet name cannot exceed 50 characters")*/
    val name: String,

    /*@field:NotBlank(message = "Pet type is required")
    @field:Size(max = 30, message = "Pet type cannot exceed 30 characters")*/
    val type: String,

    /*@field:NotNull(message = "Pet sex is required")*/
    val sex: Sex,

    /*@field:Min(0, message = "Age must be at least 0")
    @field:Max(100, message = "Age must not exceed 100")*/
    val age: Int,

)
