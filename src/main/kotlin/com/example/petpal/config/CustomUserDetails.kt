package com.example.petpal.config

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority

class CustomUserDetails(
    val id: Long,
    val email: String,
    private val _authorities: List<SimpleGrantedAuthority>
) : UserDetails {

    override fun getAuthorities(): List<SimpleGrantedAuthority> = _authorities

    override fun getPassword(): String? = null

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
