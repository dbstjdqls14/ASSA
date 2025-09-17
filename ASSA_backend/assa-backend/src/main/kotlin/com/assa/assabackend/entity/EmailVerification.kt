package com.assa.assabackend.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime


@Entity
class EmailVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    private val email: String? = null
    private val verificationToken: String? = null // 랜덤 UUID 또는 간단한 문자열
    private val createdAt: LocalDateTime? = null
    private val expiresAt: LocalDateTime? = null
    private val verified = false

}