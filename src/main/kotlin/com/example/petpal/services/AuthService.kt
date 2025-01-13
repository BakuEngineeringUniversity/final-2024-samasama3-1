package com.example.petpal.services

import com.example.petpal.dtos.LoginUserDto
import com.example.petpal.dtos.RegisterUserDto
import com.example.petpal.entities.PetEntity
import com.example.petpal.entities.UserEntity
import com.example.petpal.repositories.UserRepository
import com.example.petpal.utils.JwtUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthService(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
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
            role = registerUserDto.role
        )
        pet.user = user
        userRepository.save(user)

        logger.info("User registered successfully: ${user.email}")
        return "User and pet registered successfully."
    }

    fun loginUser(loginUserDto: LoginUserDto): String {
        logger.info("Attempting login for email: ${loginUserDto.email}")

        // Authenticate using the provided email and password
        val user = userRepository.findByEmail(loginUserDto.email)
            ?: throw IllegalArgumentException("User not found with email: ${loginUserDto.email}")

        // Validate the provided password against the stored hashed password
        if (!passwordEncoder.matches(loginUserDto.password, user.password)) {
            throw IllegalArgumentException("Invalid password")
        }

        // Generate JWT token
        val token = jwtUtils.generateToken(user.email, user.role.name)

        logger.info("Login successful for email: ${loginUserDto.email}")
        return token
    }

}
