package com.dreamsoftware.di

import com.dreamsoftware.model.AppConfig
import com.dreamsoftware.service.ISftpService
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceOrFileSource
import org.koin.dsl.module

val configModule = module {
    includes(sftpModule)
    single<AppConfig> {
        ConfigLoaderBuilder.default()
            .addResourceOrFileSource(get<ISftpService>().getFile(folderName = "config", fileName = "application", fileExt = "yml"))
            .strict()
            .build()
            .loadConfigOrThrow()
    }
    single { get<AppConfig>().mfa }
    single { get<AppConfig>().redis }
    single { get<AppConfig>().auth }
}