package com.example.petpal.config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration


@Configuration
class DatabaseConfig {
    @Value("\${spring.datasource.url}")
    val url: String? = null

    @Value("\${spring.datasource.username}")
    val username: String? = null

    @Value("\${spring.datasource.password}")
    val password: String? = null
}
