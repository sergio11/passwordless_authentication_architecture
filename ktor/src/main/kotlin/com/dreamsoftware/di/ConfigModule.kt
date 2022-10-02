package com.dreamsoftware.di

import com.dreamsoftware.model.AppConfig
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource
import org.koin.dsl.module

val configModule = module {
    single<AppConfig> {
        ConfigLoaderBuilder.default()
            .addResourceSource("/application.yml")
            .strict()
            .build()
            .loadConfigOrThrow()
    }
    single { get<AppConfig>().mfa }
    single { get<AppConfig>().redis }
}