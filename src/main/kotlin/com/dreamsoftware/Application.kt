package com.dreamsoftware

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.dreamsoftware.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureAdministration()
        configureSerialization()
        configureMonitoring()
        configureRouting()
    }.start(wait = true)
}
