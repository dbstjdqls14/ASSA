package com.assa.assabackend.service

import com.assa.assabackend.dto.UserResponse
import com.assa.assabackend.entity.AppUser
import com.assa.assabackend.repository.UserRepository
import com.assa.assabackend.repository.findByUserIdOrThrow
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
){

    private val logger = LoggerFactory.getLogger(UserService::class.java)

    fun getUser(userId: Long): AppUser{
        return userRepository.findByUserIdOrThrow(userId)
    }
}