package com.dreamsoftware.model

import com.dreamsoftware.rest.dto.ErrorResponseDTO

enum class ErrorType(val code: Int, val message: String) {
    OTP_SENDER_FAILED(100, "An error occurred when trying to send an OTP token, please try again"),
    OTP_NOT_FOUND(101, "An OTP token has not be found"),
    OTP_MAX_ATTEMPTS_ALLOWED_REACHED(102, "You've reached the max number of attempts of resending allowed for this OTP"),
    OTP_DESTINATION_IS_BLOCKED(103, "The destination specified is currently blocking, you must waiting for this block will be erased"),
    REQUEST_VALIDATION_FAILED(104, "The data provided is not valid"),
    SAVE_AUTHORIZED_CLIENT_FAILED(105, "An error occurred when trying to save client, please try again"),
    REMOVE_AUTHORIZED_CLIENT_FAILED(106, "An error occurred when trying to remove client, please try again"),
    CHECK_AUTHORIZED_CLIENT_FAILED(107, "An error occurred when trying to check client, please try again"),
    UNAUTHORIZED_CLIENT_EXCEPTION(108, "Operation denied, you don't have access")
}


fun ErrorType.toErrorResponseDTO() = ErrorResponseDTO(
    name, code, message
)