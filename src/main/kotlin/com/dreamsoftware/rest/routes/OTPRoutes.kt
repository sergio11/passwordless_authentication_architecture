package com.dreamsoftware.rest.routes

import com.dreamsoftware.rest.dto.OTPGenerationRequestDTO
import com.dreamsoftware.rest.dto.OTPVerifyRequestDTO
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureOtpRoutes() {
    route("/otp/v1") {
        post<OTPGenerationRequestDTO>("/generate") {
            call.respondText("OTPGenerationRequest CALLED!")
        }
        post<OTPVerifyRequestDTO>("/verify") {
            call.respondText("OTPVerifyRequest CALLED!")
        }
    }
}