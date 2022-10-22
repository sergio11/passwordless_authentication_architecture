package com.dreamsoftware.rest.security

import io.ktor.server.auth.*

fun AuthenticationConfig.authorizedClients(
    name: String? = null,
    configure: AuthorizedClientsAuthProvider.Config.() -> Unit
) {
    val provider = AuthorizedClientsAuthProvider(AuthorizedClientsAuthProvider.Config(name).apply(configure))
    register(provider)
}