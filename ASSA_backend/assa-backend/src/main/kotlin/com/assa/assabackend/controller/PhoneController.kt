package com.assa.assabackend.controller

import com.assa.assabackend.service.PhoneService
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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



}