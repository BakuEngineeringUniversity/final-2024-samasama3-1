package com.example.petpal.services

import com.example.petpal.dtos.LoginUserDto
import com.example.petpal.dtos.RegisterUserDto
import com.example.petpal.dtos.RegisterAdminDto
import com.example.petpal.entities.PetEntity
import com.example.petpal.entities.UserEntity
import com.example.petpal.enums.UserRoles
import com.example.petpal.repositories.UserRepository
import com.example.petpal.utils.JwtUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils
) {
    private val logger: Logger = LoggerFactory.getLogger(AuthService::class.java)
    private val passwordEncoder = BCryptPasswordEncoder()

    fun registerUser(registerUserDto: RegisterUserDto): String {
        logger.info("Attempting to register user with email: ${registerUserDto.email}")

        val hashedPassword = passwordEncoder.encode(registerUserDto.password)
        val pet = PetEntity(
            name = registerUserDto.petName,
            type = registerUserDto.petType,
            sex = registerUserDto.petSex,
            age = registerUserDto.petAge
        )

        val user = UserEntity(
            firstName = registerUserDto.firstName,
            lastName = registerUserDto.surname,
            email = registerUserDto.email,
            password = hashedPassword,
            phoneNumber = registerUserDto.phoneNumber,
            address = registerUserDto.address,
            pets = mutableListOf(pet),
            role = UserRoles.USER
        )
        pet.user = user
        userRepository.save(user)

        logger.info("User registered successfully: ${user.email}")
        return "User and pet registered successfully."
    }

    fun loginUser(loginUserDto: LoginUserDto): String {
        logger.info("Attempting login for email: ${loginUserDto.email}")

        val user = userRepository.findByEmail(loginUserDto.email)
            ?: throw IllegalArgumentException("User not found with email: ${loginUserDto.email}")

        if (!passwordEncoder.matches(loginUserDto.password, user.password)) {
            throw IllegalArgumentException("Invalid password")
        }

        val token = jwtUtils.generateToken(user.email, user.role.name, user.id)

        logger.info("Login successful for email: ${loginUserDto.email}")
        return token
    }

    fun registerAdmin(registerAdminDto: RegisterAdminDto): String {
        logger.info("Attempting to register admin with email: ${registerAdminDto.email}")

        // Check if an admin with the same email already exists
        if (userRepository.findByEmail(registerAdminDto.email) != null) {
            throw IllegalArgumentException("Admin with email ${registerAdminDto.email} already exists.")
        }

        val hashedPassword = passwordEncoder.encode(registerAdminDto.password)

        val admin = UserEntity(
            firstName = registerAdminDto.firstName,
            lastName = registerAdminDto.surname,
            email = registerAdminDto.email,
            password = hashedPassword,
            phoneNumber = registerAdminDto.phoneNumber,
            address = registerAdminDto.address,
            role = UserRoles.ADMIN, // Role is always ADMIN
            pets = mutableListOf()
        )

        userRepository.save(admin)

        logger.info("Admin registered successfully: ${admin.email}")
        return "Admin registered successfully."
    }

    fun isAdminExists(email: String): Boolean {
        return userRepository.findByEmail(email) != null
    }
}
