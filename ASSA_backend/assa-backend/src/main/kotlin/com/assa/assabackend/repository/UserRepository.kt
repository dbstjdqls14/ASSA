package com.assa.assabackend.repository

import com.assa.assabackend.entity.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<AppUser, Long> {
    fun findByEmail(email: String): AppUser?
    fun existsByEmail(email: String): Boolean
}
