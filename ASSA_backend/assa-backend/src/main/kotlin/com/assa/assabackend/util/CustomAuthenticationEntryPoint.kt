package com.assa.assabackend.util

import com.assa.assabackend.exception.InvalidTokenException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        // 이미 커밋된 응답이면 더 건드리지 않음
        if (response.isCommitted) return

        val ex = request.getAttribute("exception")
        val message = when (ex) {
            is InvalidTokenException -> "INVALID_TOKEN"
            else -> "UNAUTHORIZED"
        }

        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json;charset=UTF-8"
        response.writer.write("""{"message":"$message"}""")
    }
}
