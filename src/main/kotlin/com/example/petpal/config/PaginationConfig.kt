package com.example.petpal.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "pagination")
class PaginationConfig {
    var defaultPage: Int = 0
    var defaultSize: Int = 0
}
