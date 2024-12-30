package com.example.petpal.dtos

data class PetCreateDto(
    val name: String,
    val type: String,
    val sex: String,
    val age: Int,
    val userId: Long
)
