package com.assa.assabackend.config

import com.assa.assabackend.exception.GlobalExceptionHandler
import com.assa.assabackend.util.CustomAuthenticationEntryPoint
import com.assa.assabackend.util.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint
){

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(
        authConfig: AuthenticationConfiguration
    ): AuthenticationManager = authConfig.authenticationManager

    @Bean
    fun filterChain(http: HttpSecurity, globalExceptionHandler: GlobalExceptionHandler): SecurityFilterChain {
        http.csrf { it.disable() }
            .sessionManagement{ it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .exceptionHandling{
                it.authenticationEntryPoint(customAuthenticationEntryPoint)
            }
            .authorizeHttpRequests{ auth ->
                auth
                    .requestMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                createJwtFilterWithExclusions(),
                UsernamePasswordAuthenticationFilter::class.java
            )
        return http.build()
    }
    private fun createJwtFilterWithExclusions(): JwtAuthenticationFilter {
        val excludedPaths = listOf(
            "/auth/login",
            "/auth/register",
            "/auth/refresh",
            "/auth/logout"
        )

        return JwtAuthenticationFilter(jwtTokenProvider, excludedPaths)
    }
}//


