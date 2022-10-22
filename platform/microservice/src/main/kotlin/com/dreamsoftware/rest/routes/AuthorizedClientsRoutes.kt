package com.dreamsoftware.rest.routes

import com.dreamsoftware.plugins.ONLY_ADMIN_AUTHENTICATOR
import com.dreamsoftware.service.AuthorizedClientsService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.configureAuthorizedClientsRoutes() {

    val authorizedClientsService by inject<AuthorizedClientsService>()

    route("/clients/v1") {
        authenticate(ONLY_ADMIN_AUTHENTICATOR) {
            post("/create") {
                with(call) {
                    respond(authorizedClientsService.create(receive()))
                }
            }
            delete("/delete") {
                with(call) {
                    respond(authorizedClientsService.delete(receive()))
                }
            }
        }
    }
}