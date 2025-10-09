package com.assa.assabackend.exception

import com.assa.assabackend.dto.ErrorResponse
import com.assa.assabackend.util.ErrorType
import lombok.extern.slf4j.Slf4j
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Slf4j
class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTokenException::class)
    fun handleInvalidTokenException(): ResponseEntity<ErrorResponse> {
        val error = ErrorType.INVALID_TOKEN

        return ResponseEntity.status(error.status).body(
            ErrorResponse(
                errorContent = error.message,
                errorType = error.code,
                errorStatus = error.status
            )
        )

    }


    @ExceptionHandler(InvalidRefreshTokenException::class)
    fun handleInvalidRefreshTokenException(): ResponseEntity<ErrorResponse> {
        val error = ErrorType.INVALID_REFRESH_TOKEN
        return ResponseEntity.status(error.status).body(
            ErrorResponse(
                errorContent = error.message,
                errorType = error.code,
                errorStatus = error.status
            )
        )
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(): ResponseEntity<ErrorResponse> {
        val error = ErrorType.USER_NOT_FOUND
        return ResponseEntity.status(error.status).body(
            ErrorResponse(
                errorContent = error.message,
                errorType = error.code,
                errorStatus = error.status
            )
        )
    }

    @ExceptionHandler(InvalidCredentialException::class)
    fun handleInvalidCredentialException(): ResponseEntity<ErrorResponse> {
        val error = ErrorType.INVALID_CREDENTIAL
        return ResponseEntity.status(error.status).body(
            ErrorResponse(
                errorContent = error.message,
                errorType = error.code,
                errorStatus = error.status
            )
        )
    }


}