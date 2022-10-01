package com.dreamsoftware.di

import com.dreamsoftware.repository.OTPRepository
import com.dreamsoftware.repository.impl.OTPRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<OTPRepository> { OTPRepositoryImpl(get()) }
}