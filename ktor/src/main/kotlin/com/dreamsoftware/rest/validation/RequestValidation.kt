package com.dreamsoftware.rest.validation

import com.dreamsoftware.rest.dto.OTPGenerationRequestDTO
import com.dreamsoftware.rest.dto.OTPTypeEnum
import com.dreamsoftware.rest.dto.OTPVerifyRequestDTO
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.validateOTPGenerationRequest() {
    validate<OTPGenerationRequestDTO> { request ->
        request.validateDestination()
    }
}

fun RequestValidationConfig.validateOTPVerifyRequest() {
    validate<OTPVerifyRequestDTO> { request ->
        request.validateOtp()
    }
}

fun OTPGenerationRequestDTO.validateDestination() = if(destination.isBlank()) {
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

fun OTPVerifyRequestDTO.validateOtp() = if(otp.isBlank()) {
    ValidationResult.Invalid("OTP can not be empty, You must specify it")
} else {
    ValidationResult.Valid
}