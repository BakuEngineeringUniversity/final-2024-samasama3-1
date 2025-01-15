package com.example.petpal.common

data class PaginatedResponse<T>(
    val status: String,
    val data: List<T>,
    val message: String,
    val pagination: PaginationMetadata
)

data class PaginationMetadata(
    val currentPage: Int,
    val totalPages: Int,
    val totalItems: Long,
    val pageSize: Int
)
