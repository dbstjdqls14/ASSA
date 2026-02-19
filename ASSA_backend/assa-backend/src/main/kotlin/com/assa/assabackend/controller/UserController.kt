package com.assa.assabackend.controller

import com.assa.assabackend.config.JwtTokenProvider
import com.assa.assabackend.dto.UserMypageResponse
import com.assa.assabackend.dto.UserProfileResponse
import com.assa.assabackend.dto.UserRegisterPhoneRequest
import com.assa.assabackend.dto.UserResponse
import com.assa.assabackend.service.UserService
import org.apache.coyote.Response
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
@Validated
class UserController(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider) {

    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping("/me")
    fun getUser(authentication: Authentication): ResponseEntity<UserMypageResponse> {
        return ResponseEntity.ok(UserMypageResponse.from(
            userService.getUser( authentication.name.toLong() ))
        )
    }

    @GetMapping("/profile")
    fun getProfile(authentication: Authentication): ResponseEntity<UserProfileResponse> {
        return ResponseEntity.ok(UserProfileResponse.from(
            userService.getUser( authentication.name.toLong() )
        ))
    }



}