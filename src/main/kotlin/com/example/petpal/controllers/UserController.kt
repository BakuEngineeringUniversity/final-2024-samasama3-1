package com.example.petpal.controllers

import com.example.petpal.common.ApiResponse
import com.example.petpal.common.PaginatedResponse
import com.example.petpal.common.PaginationMetadata
import com.example.petpal.config.PaginationConfig
import com.example.petpal.entities.UserEntity
import com.example.petpal.services.UserService
import org.springframework.http.ResponseEntity
import org.slf4j.LoggerFactory
import com.example.petpal.exceptions.UserNotFoundException
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
        logger.info("Fetching users for page number: $pageNumber with default page size: ${paginationConfig.defaultSize}")

        val pageRequest = PageRequest.of(pageNumber, paginationConfig.defaultSize)

        val userPage = try {
            userService.getAllUsers(pageRequest)
        } catch (e: Exception) {
            logger.error("Error while fetching users for page: $pageNumber", e)
            throw e
        }

        logger.info("Successfully fetched ${userPage.numberOfElements} users on page: $pageNumber")

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
        logger.info("Attempting to fetch user with ID: $id")

        val user: UserEntity = try {
            userService.getUserById(id)
        } catch (e: UserNotFoundException) {
            logger.error("User with ID: $id not found", e)
            throw e
        } catch (e: Exception) {
            logger.error("An unexpected error occurred while fetching user with ID: $id", e)
            throw e
        }

        logger.info("Successfully retrieved user with ID: $id")
        return ResponseEntity.ok(
            ApiResponse(
                status = "success",
                message = "User retrieved successfully",
                data = user
            )
        )
    }

}
