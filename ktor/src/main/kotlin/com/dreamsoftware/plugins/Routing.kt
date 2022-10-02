package com.dreamsoftware.plugins

import com.dreamsoftware.rest.routes.configureOtpRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<AuthenticationException> { call, cause ->
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<AuthorizationException> { call, cause ->
            call.respond(HttpStatusCode.Forbidden)
        }
    
    }

    routing {
        trace { application.log.trace(it.buildText()) }
        get("/") {
            call.respond("Hello World!")
        }
        configureOtpRoutes()
    }
}
class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()