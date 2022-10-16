package com.dreamsoftware.plugins

import com.dreamsoftware.rest.validation.validateOTPGenerationRequest
import com.dreamsoftware.rest.validation.validateOTPVerifyRequest
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureValidation() {
    install(RequestValidation) {
        validateOTPGenerationRequest()
        validateOTPVerifyRequest()
    }
}
