package com.dreamsoftware.di

import com.dreamsoftware.model.AppConfig
import com.dreamsoftware.service.IFtpService
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceOrFileSource
import org.koin.dsl.module

val configModule = module {
    includes(ftpModule)
    single<AppConfig> {
        ConfigLoaderBuilder.default()
            .addResourceOrFileSource(get<IFtpService>().getFile(fileName = "application", fileExt = "yml"))
            .strict()
            .build()
            .loadConfigOrThrow()
    }
    single { get<AppConfig>().mfa }
    single { get<AppConfig>().redis }
    single { get<AppConfig>().auth }
}