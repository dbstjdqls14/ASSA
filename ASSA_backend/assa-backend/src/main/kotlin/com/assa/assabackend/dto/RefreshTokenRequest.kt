package com.assa.assabackend.dto

data class RefreshTokenRequest(
    val request: String
) {
    fun getCleanToken(): String {
        return request.trim()
            .removePrefix("\"")
            .removeSuffix("\"")
    }

    fun isValidJwtFormat(): Boolean {
        return getCleanToken().split(".").size == 3
    }
}