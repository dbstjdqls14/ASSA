package com.assa.assabackend.controller

import com.assa.assabackend.dto.UserRegisterPhoneRequest
import com.assa.assabackend.service.PhoneService
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@Slf4j
@RequestMapping("/phone")
class PhoneController(
    private val phoneService: PhoneService
) {

    @GetMapping("/testConnection")
    fun tc() : ResponseEntity<String>
    {
        return ResponseEntity.ok( "connection success" )
    }

    @PostMapping("/register")
    fun postPhone(authentication: Authentication,
                  @RequestBody request: UserRegisterPhoneRequest
    ): ResponseEntity<HttpStatus> {
        phoneService.postPhone(request)
        return ResponseEntity.ok(HttpStatus.OK);
    }


}