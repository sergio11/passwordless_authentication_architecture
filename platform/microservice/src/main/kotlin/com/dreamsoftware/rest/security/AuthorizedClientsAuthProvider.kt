package com.dreamsoftware.rest.security

import com.dreamsoftware.model.exception.UnauthorizedClientException
import com.dreamsoftware.repository.AuthorizedClientsRepository
import io.ktor.server.auth.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthorizedClientsAuthProvider(
    private val config: Config
): AuthenticationProvider(config), KoinComponent {

    private val authorizedClientsRepository by inject<AuthorizedClientsRepository>()

    override suspend fun onAuthenticate(context: AuthenticationContext) {
        context.call.request.headers[config.headerName]?.let { clientId ->
            if(authorizedClientsRepository.exists(clientId)) {
                context.principal(UserIdPrincipal(clientId))
            } else {
                throw UnauthorizedClientException("Authorization Failed")
            }
        } ?: throw UnauthorizedClientException("Authorization Request must be provided")
    }

    class Config internal constructor(name: String?) : AuthenticationProvider.Config(name) {
        var headerName: String = "ClientId"
    }
}