package com.dreamsoftware.rest.validation

import com.dreamsoftware.rest.dto.CancelOperationDTO
import com.dreamsoftware.rest.dto.OTPGenerationRequestDTO
import com.dreamsoftware.rest.dto.OTPTypeEnum
import com.dreamsoftware.rest.dto.OTPVerifyRequestDTO
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.configureOtpRoutingValidation() {
    validateOTPGenerationRequest()
    validateOTPVerifyRequest()
    validateCancelOperationDTO()
    validateOTPResendRequestDTO()
}

private fun RequestValidationConfig.validateOTPGenerationRequest() {
    validate<OTPGenerationRequestDTO> { request ->
        request.validateDestination()
    }
}

private fun RequestValidationConfig.validateOTPVerifyRequest() {
    validate<OTPVerifyRequestDTO> { request ->
        if(request.otp.isBlank()) {
            ValidationResult.Invalid("OTP can not be empty, You must specify it")
        } else {
            ValidationResult.Valid
        }
    }
}

private fun RequestValidationConfig.validateCancelOperationDTO() {
    validate<CancelOperationDTO> { request ->
        if(request.operationId.isBlank()) {
            ValidationResult.Invalid("Operation Id can not be empty, You must specify it")
        } else {
            ValidationResult.Valid
        }
    }
}

private fun RequestValidationConfig.validateOTPResendRequestDTO() {
    validate<CancelOperationDTO> { request ->
        if(request.operationId.isBlank()) {
            ValidationResult.Invalid("Operation Id can not be empty, You must specify it")
        } else {
            ValidationResult.Valid
        }
    }
}


private fun OTPGenerationRequestDTO.validateDestination() = if(destination.isBlank()) {
    ValidationResult.Invalid("Destination can not be empty, You must specify it")
} else {
    when(type) {
        OTPTypeEnum.MAIL -> {
            if(destination.matches(Regex("^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$"))) {
                ValidationResult.Valid
            } else {
                ValidationResult.Invalid("Destination must be a valid email address")
            }
        }
        OTPTypeEnum.PUSH -> {
            if(destination.matches(Regex("/^(?:[\\w-]*\\.){2}[\\w-]*\$/"))) {
                ValidationResult.Valid
            } else {
                ValidationResult.Invalid("Destination must be a valid FCM token")
            }
        }
        OTPTypeEnum.SMS -> {
            val patterns = ("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                    + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                    + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$")
            if(destination.matches(Regex(patterns))) {
                ValidationResult.Valid
            } else {
                ValidationResult.Invalid("Destination must be a valid phone number")
            }
        }
    }
}