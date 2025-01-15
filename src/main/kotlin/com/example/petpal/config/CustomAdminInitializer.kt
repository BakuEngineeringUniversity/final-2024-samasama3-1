package com.example.petpal.config

import com.example.petpal.dtos.RegisterAdminDto
import com.example.petpal.services.AuthService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class CustomAdminInitializer(
    private val customAdminConfig: CustomAdminConfig,
    private val authService: AuthService
) : CommandLineRunner {

    private val logger: Logger = LoggerFactory.getLogger(CustomAdminInitializer::class.java)

    override fun run(vararg args: String?) {
        logger.info("Checking if the custom admin exists...")

        if (!authService.isAdminExists(customAdminConfig.email)) {
            logger.info("Custom admin not found. Creating one...")

            val registerAdminDto = RegisterAdminDto(
                firstName = customAdminConfig.firstName,
                surname = customAdminConfig.lastName,
                email = customAdminConfig.email,
                password = customAdminConfig.password,
                phoneNumber = customAdminConfig.phoneNumber,
                address = customAdminConfig.address
            )

            authService.registerAdmin(registerAdminDto)
            logger.info("Custom admin created successfully: ${customAdminConfig.email}")
        } else {
            logger.info("Custom admin already exists: ${customAdminConfig.email}")
        }
    }
}
