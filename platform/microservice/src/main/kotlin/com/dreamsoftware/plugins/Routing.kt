package com.dreamsoftware.plugins

import com.dreamsoftware.model.ErrorType
import com.dreamsoftware.model.exception.*
import com.dreamsoftware.model.toErrorResponseDTO
import com.dreamsoftware.rest.routes.configureOtpRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<AuthenticationException> { call, cause ->
            call.respond(HttpStatusCode.Unauthorized, cause)
        }
        exception<AuthorizationException> { call, cause ->
            call.respond(HttpStatusCode.Forbidden, cause)
        }
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest,
                ErrorType.REQUEST_VALIDATION_FAILED.toErrorResponseDTO().copy(details = cause.reasons.joinToString()))
        }
        exception<OTPSenderFailedException> { call, _ ->
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorType.OTP_SENDER_FAILED.toErrorResponseDTO()
            )
        }
        exception<OTPNotFoundException> { call, _ ->
            call.respond(
                HttpStatusCode.NotFound,
                ErrorType.OTP_NOT_FOUND.toErrorResponseDTO()
            )
        }
        exception<OTPMaxAttemptsAllowedReachedException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest,
                ErrorType.OTP_MAX_ATTEMPTS_ALLOWED_REACHED.toErrorResponseDTO())
        }
        exception<OTPDestinationIsBlockedException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest,
                ErrorType.OTP_DESTINATION_IS_BLOCKED.toErrorResponseDTO())
        }
    }

    routing {
        trace { application.log.trace(it.buildText()) }
        configureOtpRoutes()
    }
}