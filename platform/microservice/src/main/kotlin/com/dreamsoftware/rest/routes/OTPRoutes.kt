package com.dreamsoftware.rest.routes

import com.dreamsoftware.service.OTPService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.configureOtpRoutes() {

    val otpService by inject<OTPService>()

    route("/otp/v1") {
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