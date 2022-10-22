package com.dreamsoftware.plugins

import com.dreamsoftware.model.ErrorType
import com.dreamsoftware.model.toErrorResponseDTO
import com.dreamsoftware.rest.routes.configureAuthorizedClientsRoutes
import com.dreamsoftware.rest.routes.configureAuthorizedClientsStatusPages
import com.dreamsoftware.rest.routes.configureOtpRoutes
import com.dreamsoftware.rest.routes.configureOtpStatusPages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest,
                ErrorType.REQUEST_VALIDATION_FAILED.toErrorResponseDTO().copy(details = cause.reasons.joinToString()))
        }
        configureAuthorizedClientsStatusPages()
        configureOtpStatusPages()
    }

    routing {
        trace { application.log.trace(it.buildText()) }
        configureOtpRoutes()
        configureAuthorizedClientsRoutes()
    }
}