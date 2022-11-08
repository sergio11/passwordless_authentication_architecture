package com.dreamsoftware.plugins

import com.dreamsoftware.rest.validation.configureAuthorizedClientsRoutingValidation
import com.dreamsoftware.rest.validation.configureOtpRoutingValidation
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureValidation() {
    install(RequestValidation) {
        configureOtpRoutingValidation()
        configureAuthorizedClientsRoutingValidation()
    }
}
