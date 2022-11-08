package com.dreamsoftware.rest.validation

import com.dreamsoftware.rest.dto.CreateAuthorizedClientDTO
import com.dreamsoftware.rest.dto.DeleteAuthorizedClientDTO
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.configureAuthorizedClientsRoutingValidation() {
    validateCreateAuthorizedClientDTO()
    validateDeleteAuthorizedClientDTO()
}

private fun RequestValidationConfig.validateCreateAuthorizedClientDTO() {
    validate<CreateAuthorizedClientDTO> { request ->
        with(request) {
            if(name.isBlank()) {
                ValidationResult.Invalid("Client name can not be empty, You must specify it")
            } else {
                ValidationResult.Valid
            }
            if(password.isBlank()) {
                ValidationResult.Invalid("Client password can not be empty, You must specify it")
            } else {
                ValidationResult.Valid
            }
        }
    }
}

private fun RequestValidationConfig.validateDeleteAuthorizedClientDTO() {
    validate<DeleteAuthorizedClientDTO> { request ->
        if(request.id.isBlank()) {
            ValidationResult.Invalid("Client Id can not be empty, You must specify it")
        } else {
            ValidationResult.Valid
        }
    }
}