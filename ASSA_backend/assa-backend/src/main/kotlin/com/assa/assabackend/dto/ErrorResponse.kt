package com.assa.assabackend.dto

import com.assa.assabackend.util.ErrorType

data class ErrorResponse (
    val errorContent: String,
    val errorType: String,
    val errorStatus: Int
) {
    companion object {
        fun of(errorType: ErrorType): ErrorResponse {
            return ErrorResponse(
                errorType = errorType.code,
                errorContent = errorType.message,
                errorStatus = errorType.status
            )
        }
    }
}