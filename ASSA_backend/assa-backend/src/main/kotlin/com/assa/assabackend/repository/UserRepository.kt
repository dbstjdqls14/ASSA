package com.assa.assabackend.repository

import com.assa.assabackend.entity.AppUser
import com.assa.assabackend.exception.UserNotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<AppUser, Long> {
    fun findByUserId(userId: Long): AppUser?
    fun findByEmail(email: String): AppUser?
    fun existsByEmail(email: String): Boolean

}


fun UserRepository.findByUserIdOrThrow(userId: Long): AppUser =
    findByIdOrNull(userId) ?: throw UserNotFoundException()