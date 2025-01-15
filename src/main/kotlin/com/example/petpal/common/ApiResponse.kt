package com.example.petpal.common

data class ApiResponse<T>(
    val status: String,
    val message: String,
    val data: T? = null,
    val errorCode: String? = null
)
