package com.assa.assabackend

import com.assa.assabackend.config.JwtTokenProvider
import com.assa.assabackend.exception.UserNotFoundException
import com.assa.assabackend.repository.UserRepository
import com.assa.assabackend.repository.findByUserIdOrThrow
import com.assa.assabackend.service.AuthService
import com.assa.assabackend.util.ErrorType
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.Test

@DisplayName("AuthService - Token refresh 테스트")
class AuthServiceRefreshTest {
    // 모든 의존성 Mock 생성
    private val redisTemplate = mockk<RedisTemplate<String, String>>()
    private val mailSender = mockk<JavaMailSender>()
    private val userRepository = mockk<UserRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val jwtTokenProvider = mockk<JwtTokenProvider>()
    private val authentication = mockk<Authentication>()

    private lateinit var authService: AuthService

    @BeforeEach
    fun setUp() {
        // 실제 생성자 순서에 맞게 수정
        authService = AuthService(
            redisTemplate = redisTemplate,
            mailSender = mailSender,
            userRepository = userRepository,
            passwordEncoder = passwordEncoder,
            jwtTokenProvider = jwtTokenProvider
        )
        clearAllMocks()
    }


    @Test
    @DisplayName("존재하지 않는 사용자로 UserNotFoundException 발생")
    fun `refresh - 존재하지 않는 사용자`() {
        val userId = 999L
        val refreshToken = "valid-refresh-token"

        every { authentication.name } returns userId.toString()
        every { jwtTokenProvider.validateRefreshToken(refreshToken, userId) } returns true
        every { userRepository.findByUserIdOrThrow(userId) } throws UserNotFoundException()

        verify(exactly = 1) { authentication.name }
        verify(exactly = 1) { jwtTokenProvider.validateRefreshToken(refreshToken, userId) }
        verify(exactly = 1) { userRepository.findByUserIdOrThrow(userId) }
        verify(exactly = 0) { jwtTokenProvider.generateAccessToken(any()) }
    }

}