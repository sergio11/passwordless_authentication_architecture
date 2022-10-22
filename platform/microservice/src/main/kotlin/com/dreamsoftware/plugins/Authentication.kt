package com.dreamsoftware.plugins

import com.dreamsoftware.di.AUTHENTICATION_REALM
import com.dreamsoftware.di.AUTHENTICATION_USER_TABLE
import com.dreamsoftware.rest.security.authorizedClients
import io.ktor.server.application.*
import io.ktor.server.auth.*
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject

const val ONLY_ADMIN_AUTHENTICATOR = "auth-digest"
const val AUTHORIZED_CLIENT_AUTHENTICATOR = "auth-client"

fun Application.configureAuthentication() {
    val realName by inject<String>(named(AUTHENTICATION_REALM))
    val userTable by inject<Map<String, ByteArray>>(named(AUTHENTICATION_USER_TABLE))

    install(Authentication) {
        digest(ONLY_ADMIN_AUTHENTICATOR) {
            realm = realName
            digestProvider { userName, _ ->
                userTable[userName]
            }
        }
        authorizedClients(AUTHORIZED_CLIENT_AUTHENTICATOR) {}
    }
}
