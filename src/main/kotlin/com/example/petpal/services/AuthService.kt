package com.example.petpal.services

import com.example.petpal.dtos.LoginUserDto
import com.example.petpal.dtos.RegisterUserDto
import com.example.petpal.entities.PetEntity
import com.example.petpal.entities.UserEntity
import com.example.petpal.repositories.UserRepository
import com.example.petpal.utils.JwtUtils
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
    private val passwordEncoder = BCryptPasswordEncoder()

    fun registerUser(registerUserDto: RegisterUserDto): String {
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
            pets = mutableListOf(pet)
        )
        pet.user = user
        userRepository.save(user)
        return "User and pet registered successfully."
    }

    fun loginUser(loginUserDto: LoginUserDto): String {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginUserDto.email,
                loginUserDto.password
            )
        )
        return jwtUtils.generateToken(authentication.name)
    }
}
