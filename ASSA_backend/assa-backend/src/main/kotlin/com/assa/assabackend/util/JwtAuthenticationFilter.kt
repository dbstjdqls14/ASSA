package com.assa.assabackend.util

import com.assa.assabackend.config.JwtTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = getTokenFromRequest(request)

//        if (token != null && jwtTokenProvider.validateToken(token)) { // 조건문 선후 관게 중요
////            val email = jwtTokenProvider.getEmailFromToken(token)
//            val userDetails = userDetailsService.loadUserByUsername(email)
//
//            val authentication = UsernamePasswordAuthenticationToken(
//                userDetails, null, userDetails.authorities
//            )
//            SecurityContextHolder.getContext().authentication = authentication
//        }

        filterChain.doFilter(request, response);
    }
    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}
/**
 * jdbc 속도차이
 * jpa
 * 부하테스트
 * 쉘 직접연결
 */