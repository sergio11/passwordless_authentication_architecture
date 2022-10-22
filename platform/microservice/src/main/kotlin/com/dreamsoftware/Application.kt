package com.dreamsoftware

import com.dreamsoftware.plugins.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureAdministration()
        configureSerialization()
        configureValidation()
        configureMonitoring()
        configureAuthentication()
        configureKoin()
        configureRouting()
    }.start(wait = true)
}
