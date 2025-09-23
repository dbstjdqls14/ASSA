package com.assa.assabackend.controller

import com.assa.assabackend.config.JwtTokenProvider
import com.assa.assabackend.dto.UserResponse
import com.assa.assabackend.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@Validated
class UserController(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider) {

    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping("/me")
    fun getUser(authentication: Authentication): ResponseEntity<UserResponse> {
        logger.info("사용자 정보 조회 요청")
        logger.info("Authentication name: ${authentication.name}")
        logger.info("Authentication authorities: ${authentication.authorities}")

        // authentication.name에는 이미 userId가 문자열로 들어있음!
        val userId = authentication.name.toLong()  // ✅ 직접 변환
        logger.info("추출된 사용자 ID: $userId")

        val user = userService.getUser(userId)
        logger.info("사용자 이메일: ${user.email}")

        return ResponseEntity.ok(UserResponse.from(user))
    }


}