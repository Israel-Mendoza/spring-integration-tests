package dev.artisra.integration.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun getSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic(Customizer.withDefaults())
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.POST, "/people/new").authenticated()
                it.requestMatchers(HttpMethod.GET, "/**").authenticated()
            }
        return http.build()
    }
}