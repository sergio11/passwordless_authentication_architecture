package com.dreamsoftware.model

import com.dreamsoftware.rest.dto.ErrorResponseDTO

enum class ErrorType(val code: Int, val message: String) {
    OTP_SENDER_FAILED(100, "An error occurred when trying to send an OTP token, please try again"),
    OTP_NOT_FOUND(101, "An OTP token has not be found"),
    OTP_MAX_ATTEMPTS_ALLOWED_REACHED(102, "You've reached the max number of attempts of resending allowed for this OTP")
}


fun ErrorType.toErrorResponseDTO() = ErrorResponseDTO(
    name, code, message
)