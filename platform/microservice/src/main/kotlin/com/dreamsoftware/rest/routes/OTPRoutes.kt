package com.dreamsoftware.rest.routes

import com.dreamsoftware.model.ErrorType
import com.dreamsoftware.model.exception.OTPDestinationIsBlockedException
import com.dreamsoftware.model.exception.OTPMaxAttemptsAllowedReachedException
import com.dreamsoftware.model.exception.OTPNotFoundException
import com.dreamsoftware.model.exception.OTPSenderFailedException
import com.dreamsoftware.model.toErrorResponseDTO
import com.dreamsoftware.plugins.AUTHORIZED_CLIENT_AUTHENTICATOR
import com.dreamsoftware.service.OTPService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.configureOtpRoutes() {

    val otpService by inject<OTPService>()

    route("/otp/v1") {
        authenticate(AUTHORIZED_CLIENT_AUTHENTICATOR) {
            post("/generate") {
                with(call) {
                    respond(otpService.generate(receive()))
                }
            }
            post("/verify") {
                with(call) {
                    respond(otpService.verify(receive()))
                }
            }
            post("/resend") {
                with(call) {
                    respond(otpService.resend(receive()))
                }
            }
        }
    }
}

fun StatusPagesConfig.configureOtpStatusPages() {
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
        call.respond(
            HttpStatusCode.BadRequest,
            ErrorType.OTP_MAX_ATTEMPTS_ALLOWED_REACHED.toErrorResponseDTO())
    }
    exception<OTPDestinationIsBlockedException> { call, _ ->
        call.respond(
            HttpStatusCode.BadRequest,
            ErrorType.OTP_DESTINATION_IS_BLOCKED.toErrorResponseDTO())
    }
}