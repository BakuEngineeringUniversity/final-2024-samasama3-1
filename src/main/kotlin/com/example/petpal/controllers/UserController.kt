package com.example.petpal.controllers

import com.example.petpal.common.ApiResponse
import com.example.petpal.common.PaginatedResponse
import com.example.petpal.common.PaginationMetadata
import com.example.petpal.config.PaginationConfig
import com.example.petpal.entities.UserEntity
import com.example.petpal.services.UserService
import org.springframework.http.ResponseEntity
import org.slf4j.LoggerFactory

import org.springframework.data.domain.PageRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val paginationConfig: PaginationConfig
) {

    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping("/page/{pageNumber}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    fun getAllUsers(@PathVariable pageNumber: Int): ResponseEntity<PaginatedResponse<UserEntity>> {
        logger.info("Fetching users on page: $pageNumber with default page size")

        // Use the default size from PaginationConfig
        val pageRequest = PageRequest.of(pageNumber, paginationConfig.defaultSize)
        val userPage = userService.getAllUsers(pageRequest)

        // Construct PaginatedResponse using the existing data classes
        return ResponseEntity.ok(
            PaginatedResponse(
                status = "success",
                data = userPage.content,
                message = "Users retrieved successfully",
                pagination = PaginationMetadata(
                    currentPage = userPage.number,
                    totalPages = userPage.totalPages,
                    totalItems = userPage.totalElements,
                    pageSize = userPage.size
                )
            )
        )
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    fun getUserById(@PathVariable id: Long): ResponseEntity<ApiResponse<UserEntity>> {
        logger.info("Fetching user with ID: $id")
        val user = userService.getUserById(id)
        logger.info("User with ID: $id retrieved successfully")
        return ResponseEntity.ok(ApiResponse("success", user, "User retrieved successfully"))
    }

}
