package com.example.petpal.dtos

data class RegisterUserDto(
    val email: String,
    val firstName: String,
    val surname: String,
    val password: String,
    val phoneNumber: String,
    val address: String,
    val petName: String,
    val petSex: String,
    val petType: String,
    val petAge: Int
)
