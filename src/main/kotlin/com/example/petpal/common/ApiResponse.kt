package com.example.petpal.common

data class ApiResponse<T>(
    val status: String,
    val data: T?,
    val message: String
)
