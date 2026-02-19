package com.assa.assabackend.controller

import com.assa.assabackend.dto.*
import com.assa.assabackend.service.AuthService
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
@Validated
class AuthController (
    private val authService: AuthService
) {


    /**
     * 이메일 인증번호 발송
     */
    @PostMapping("/send-verification")
    fun sendVerificationCode(
        @Valid @RequestBody request: SendVerificationRequest
    ): ResponseEntity<ApiResponse<VerificationStatusResponse>> {
        val response = authService.sendVerificationCode(request.email)
        val status = if (response.success) HttpStatus.OK else HttpStatus.BAD_REQUEST
        return ResponseEntity(response, status)
    }

    /**
     * 인증번호 검증
     */
    @PostMapping("/verify-code")
    fun verifyCode(
        @Valid @RequestBody request: VerifyCodeRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        val response = authService.verifyCode(request.email, request.code)
        val status = if (response.success) HttpStatus.OK else HttpStatus.BAD_REQUEST
        return ResponseEntity(response, status)
    }

    /**
     * 인증 상태 확인
     * 회원가입 시도 시에 이게 true가 떠야함.
     */
    @GetMapping("/verification-status")
    fun getVerificationStatus(
        @RequestParam @Email email: String
    ): ResponseEntity<ApiResponse<VerificationStatusResponse>> {
        val response = authService.getVerificationStatus(email)
        return ResponseEntity.ok(response)
    }

    /**
     * 회원가입
     * 필요 정보 다 입력 시.
     */
    @PostMapping("/signup")
    fun signup(
        @Valid @RequestBody request: SignupRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        println("controller 진입")
        val response = authService.signup(request)
        val status = if (response.success) HttpStatus.OK else HttpStatus.BAD_REQUEST
        return ResponseEntity(response, status)
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: LoginRequest
    ): ResponseEntity<TokenResponse> {
        val tokenResponse = authService.login(request)
        return ResponseEntity(tokenResponse, HttpStatus.OK)
    }

    @PostMapping("/logout")
    fun logout(
        authentication: Authentication
    ): ResponseEntity<String> {
        authService.logout(authentication)
        return ResponseEntity("LOGOUT SUCCESS", HttpStatus.OK)
    }

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody @Valid refreshTokenRequest: RefreshTokenRequest
    ): ResponseEntity<TokenResponse> {
        val token = refreshTokenRequest.getCleanToken()
        val tokenResponse = authService.refresh(token)

        return ResponseEntity(tokenResponse, HttpStatus.OK)
    }
}