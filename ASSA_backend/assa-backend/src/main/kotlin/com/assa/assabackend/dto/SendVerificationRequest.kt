package com.assa.assabackend.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SendVerificationRequest(
    @field:Email(message = "올바른 이메일 형식이 아닙니다")
    @field:NotBlank(message = "이메일을 입력해주세요")
    val email: String
)
