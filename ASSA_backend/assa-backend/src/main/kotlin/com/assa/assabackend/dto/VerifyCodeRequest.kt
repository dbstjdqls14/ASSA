package com.assa.assabackend.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class VerifyCodeRequest(
    @field:Email(message = "올바른 이메일 형식이 아닙니다")
    @field:NotBlank(message = "이메일을 입력해주세요")
    val email: String,

    @field:NotBlank(message = "인증번호를 입력해주세요")
    @field:Pattern(regexp = "^[0-9]{6}$", message = "인증번호는 6자리 숫자입니다")
    val code: String
)
