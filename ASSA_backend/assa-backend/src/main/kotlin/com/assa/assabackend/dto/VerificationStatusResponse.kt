package com.assa.assabackend.dto

data class VerificationStatusResponse(
    val remainingTime: Long, // 남은 시간(초)
    val attemptsLeft: Int    // 남은 시도 횟수
)
