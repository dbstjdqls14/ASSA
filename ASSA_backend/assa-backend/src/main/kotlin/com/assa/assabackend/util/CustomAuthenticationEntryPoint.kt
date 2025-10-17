package com.assa.assabackend.util

import com.assa.assabackend.exception.InvalidTokenException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import org.springframework.security.web.AuthenticationEntryPoint

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val exception = request.getAttribute("exception")

        if (exception is InvalidTokenException) {
            throw exception
        }

        throw InvalidTokenException()
    }

}