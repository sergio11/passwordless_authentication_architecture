package com.dreamsoftware.di

import com.dreamsoftware.model.AuthConfig
import com.dreamsoftware.utils.md5Digest
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val AUTHENTICATION_USER_TABLE = "AUTH_USER_TABLE"
const val AUTHENTICATION_REALM = "AUTH_REALM"

val authModule = module {
    single(named(AUTHENTICATION_USER_TABLE)) {
        get<AuthConfig>().let {
            mapOf(
                it.adminUser to "${it.adminUser}:${it.realmName}:${it.password}".md5Digest()
            )
        }
    }
    single(named(AUTHENTICATION_REALM)) { get<AuthConfig>().realmName }
}