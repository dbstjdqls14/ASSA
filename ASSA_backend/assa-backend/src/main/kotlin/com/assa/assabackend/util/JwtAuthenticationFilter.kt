package com.assa.assabackend.util

import com.assa.assabackend.config.JwtTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component  //
class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val excludedPaths: List<String> = emptyList()
) : OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val requestPath = request.requestURI

        val allExcludedPaths = excludedPaths

        return allExcludedPaths.any { excludedPath ->
            when {
                excludedPath.endsWith("/**") -> {
                    val basePath = excludedPath.removeSuffix("/**")
                    requestPath.startsWith(basePath)
                }
                excludedPath.contains("*") -> {
                    requestPath.matches(excludedPath.replace("*", ".*").toRegex())
                }
                else -> requestPath == excludedPath
            }
        }
    }
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestURI = request.requestURI
        val method = request.method

        logger.info("JWT 필터 진입, - $method $requestURI")

        try {
            val token = getTokenFromRequest(request)
            logger.info("토큰 추출 결과: ${if (token != null) "토큰 있음 (${token.take(20)}...)" else "토큰 없음"}")

            if (token != null && jwtTokenProvider.validateToken(token)) {
                logger.info("토큰 검증 성공")

                // JWT에서 사용자 정보 추출
                val userId = jwtTokenProvider.getUserIdFromToken(token)

                logger.info("사용자 정보 - userId: $userId")

                val authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))

                val authentication = UsernamePasswordAuthenticationToken(
                    userId.toString(),
                    null,               // credentials는 null (JWT 방식)
                    authorities         // 권한 목록, 아마2개?
                )

                // SecurityContext에 인증 정보 설정
                SecurityContextHolder.getContext().authentication = authentication
                logger.info(" SecurityContext에 저장 1 ")

                // 설정 확인
                val currentAuth = SecurityContextHolder.getContext().authentication
                logger.info("현재 인증 정보 - name: ${currentAuth?.name}, authorities: ${currentAuth?.authorities}")

            } else {
                if (token != null) {
                    logger.warn(" 토큰 검증 실패")
                } else {
                    logger.info("토큰 없음")
                }
            }

        } catch (e: Exception) {
            logger.error("JWT 필터 처리 중 오류: ${e.message}", e)
            SecurityContextHolder.clearContext()
        }

        filterChain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        logger.info(" Authorization 헤더: ${bearerToken?.take(50)}...")

        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            val token = bearerToken.substring(7)
            token
        } else {
            logger.info("Bearer 토큰 형식이 아니거나 헤더가 없음")
            null
        }
    }
}
