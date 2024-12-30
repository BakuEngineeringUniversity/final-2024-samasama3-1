package com.example.petpal.dtos

data class PetUpdateDto(
    val name: String?,
    val type: String?,
    val sex: String?,
    val age: Int?
)
