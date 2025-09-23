package com.assa.assabackend.repository

import com.assa.assabackend.entity.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<AppUser, Long> {
    fun findByUserId(userId: Long): AppUser
    fun findByEmail(email: String): AppUser?
    fun existsByEmail(email: String): Boolean
}
