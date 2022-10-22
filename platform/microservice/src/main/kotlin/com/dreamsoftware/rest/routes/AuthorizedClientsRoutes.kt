package com.dreamsoftware.rest.routes

import com.dreamsoftware.model.ErrorType
import com.dreamsoftware.model.exception.AuthorizedClientsCheckException
import com.dreamsoftware.model.exception.AuthorizedClientsRemoveException
import com.dreamsoftware.model.exception.AuthorizedClientsSaveException
import com.dreamsoftware.model.exception.UnauthorizedClientException
import com.dreamsoftware.model.toErrorResponseDTO
import com.dreamsoftware.plugins.ONLY_ADMIN_AUTHENTICATOR
import com.dreamsoftware.service.AuthorizedClientsService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.statuspages.*
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

fun StatusPagesConfig.configureAuthorizedClientsStatusPages() {
    exception<AuthorizedClientsSaveException> { call, _ ->
        call.respond(
            HttpStatusCode.InternalServerError,
            ErrorType.SAVE_AUTHORIZED_CLIENT_FAILED.toErrorResponseDTO()
        )
    }

    exception<AuthorizedClientsRemoveException> { call, _ ->
        call.respond(
            HttpStatusCode.InternalServerError,
            ErrorType.REMOVE_AUTHORIZED_CLIENT_FAILED.toErrorResponseDTO()
        )
    }

    exception<AuthorizedClientsCheckException> { call, _ ->
        call.respond(
            HttpStatusCode.InternalServerError,
            ErrorType.CHECK_AUTHORIZED_CLIENT_FAILED.toErrorResponseDTO()
        )
    }
    exception<UnauthorizedClientException> { call, _ ->
        call.respond(
            HttpStatusCode.Forbidden,
            ErrorType.UNAUTHORIZED_CLIENT_EXCEPTION.toErrorResponseDTO()
        )
    }

}