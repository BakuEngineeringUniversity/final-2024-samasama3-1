package com.example.petpal.services


import com.example.petpal.dtos.LoginUserDto
import com.example.petpal.dtos.RegisterUserDto
import com.example.petpal.entites.PetEntity
import com.example.petpal.entites.UserEntity
import com.example.petpal.repositories.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.Date
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value

@Service
class AuthService(
    private val userRepository: UserRepository,
    @Value("\${jwt.secret}") private val jwtSecret: String
) {
    private val passwordEncoder = BCryptPasswordEncoder()

    fun registerUser(registerUserDto: RegisterUserDto): String {
        val hashedPassword = passwordEncoder.encode(registerUserDto.password)

        val pet = PetEntity(
            name = registerUserDto.name,
            type = registerUserDto.type,
            sex = registerUserDto.sex,
            age = registerUserDto.age
        )

        val user = UserEntity(
            firstName = registerUserDto.firstName,
            lastName = registerUserDto.surname,
            email = registerUserDto.email,
            password = hashedPassword,
            phoneNumber = registerUserDto.phoneNumber,
            address = registerUserDto.address,
            pets = listOf(pet).toMutableList()
        )

        pet.user = user

        userRepository.save(user)

        return "User registered successfully."
    }

    fun loginUser(loginUserDto: LoginUserDto): String {
        val user = userRepository.findByEmail(loginUserDto.email)
            ?: throw IllegalArgumentException("Invalid email or password")

        if (!passwordEncoder.matches(loginUserDto.password, user.password)) {
            throw IllegalArgumentException("Invalid email or password")
        }

        return generateJwtToken(user.email)
    }

    private fun generateJwtToken(email: String): String {
        val now = Date()
        val expiryDate = Date(now.time + 86400000)

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }
}