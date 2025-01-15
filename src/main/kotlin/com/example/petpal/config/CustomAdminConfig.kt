package com.example.petpal.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "custom-admin")
class CustomAdminConfig {
    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var email: String
    lateinit var password: String
    lateinit var phoneNumber: String
    lateinit var address: String
    lateinit var role: String
}
