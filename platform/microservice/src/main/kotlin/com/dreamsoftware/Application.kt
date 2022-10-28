package com.dreamsoftware

import com.dreamsoftware.plugins.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureKoin()
        configureAdministration()
        configureSerialization()
        configureValidation()
        configureMonitoring()
        configureAuthentication()
        configureRouting()
    }.start(wait = true)
}
