package com.example.petpal.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtils(
    @Value("\${jwt.secret}") private val jwtSecret: String,
    @Value("\${jwt.expiration}") private val jwtExpirationMs: Long
) {
    private val key = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    fun generateToken(email: String, role: String, userId: Long): String {
        return Jwts.builder()
            .setSubject(email)
            .claim("role", role)
            .claim("id", userId)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = getClaimsFromToken(token)
            println("Token claims: $claims")
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            println("Token validation failed: ${e.message}")
            false
        }
    }


    fun getEmailFromJwt(token: String): String {
        return getClaimsFromToken(token).subject
    }

    fun getRoleFromJwt(token: String): String {
        return getClaimsFromToken(token).get("role", String::class.java)
    }

    private fun getClaimsFromToken(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun getIdFromJwt(token: String): Long {
        val claims = getClaimsFromToken(token)
        return (claims["id"] as Int).toLong()
    }

}
