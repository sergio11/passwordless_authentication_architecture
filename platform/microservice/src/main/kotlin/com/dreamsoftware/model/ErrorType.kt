package com.dreamsoftware.model

import com.dreamsoftware.rest.dto.ErrorResponseDTO

enum class ErrorType(val code: Int, val message: String) {
    OTP_SENDER_FAILED(100, "An error occurred when trying to send an OTP token, please try again"),
    OTP_NOT_FOUND(101, "An OTP token has not be found"),
    OTP_MAX_ATTEMPTS_ALLOWED_REACHED(102, "You've reached the max number of attempts of resending allowed for this OTP"),
    OTP_DESTINATION_IS_BLOCKED(103, "The destination specified is currently blocking, you must waiting for this block will be erased"),
    REQUEST_VALIDATION_FAILED(104, "The data provided is not valid"),
}


fun ErrorType.toErrorResponseDTO() = ErrorResponseDTO(
    name, code, message
)