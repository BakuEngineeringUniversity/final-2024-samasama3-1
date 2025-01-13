package com.example.petpal.config

import com.example.petpal.utils.JwtUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.security.core.authority.SimpleGrantedAuthority

class JwtAuthenticationFilter(private val jwtUtils: JwtUtils) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = extractJwtFromRequest(request)
            if (!token.isNullOrEmpty() && jwtUtils.validateToken(token)) {
                val email = jwtUtils.getEmailFromJwt(token)
                val role = jwtUtils.getRoleFromJwt(token)
                val id = jwtUtils.getIdFromJwt(token) // Extract user ID
                val authorities = listOf(SimpleGrantedAuthority("ROLE_$role"))

                val customUserDetails = CustomUserDetails(
                    id = id,
                    email = email,
                    _authorities = authorities
                )

                println("Authenticated user: $email with role: $role and ID: $id")

                val authentication = UsernamePasswordAuthenticationToken(customUserDetails, null, authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            } else {
                println("Invalid or missing JWT token")
            }
        } catch (e: Exception) {
            println("Error in JwtAuthenticationFilter: ${e.message}")
            e.printStackTrace()
        }

        filterChain.doFilter(request, response)
    }

    private fun extractJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        println("Authorization Header: $bearerToken") // Debugging log
        return if (!bearerToken.isNullOrEmpty() && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7) // Remove "Bearer " prefix
        } else {
            null
        }
    }


}
