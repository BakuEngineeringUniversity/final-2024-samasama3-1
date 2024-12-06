package com.example.petpal.dtos

data class RegisterUserDto(
    // User
    val email: String,
    val firstName: String,
    val surname: String,
    val password: String,
    val phoneNumber: String,
    val address: String,

    // Pet
    val name: String,
    val sex: String,
    val type: String,
    val age: Int
)