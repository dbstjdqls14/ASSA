package com.assa.assabackend.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignupRequest (
    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호를 입력해주세요.")
    @field:Size(min = 8, message = "비밀번호는 최소 8자리입니다.")
    val password: String,

    @field:NotBlank(message = "이름을 입력해주세요.")
    val name: String,

    val region_metro_id: Long,

    val region_district_id: Long,

//    val phone_id: Long,

    val profile_path: String,

    )

/**user_id
region_metro_id
region_district_id
name
email
phone_id
profile_path
is_deleted
deleted_time
created_time
password
email_verified
        */