package com.dreamsoftware.di

import com.dreamsoftware.service.OTPService
import com.dreamsoftware.service.impl.OTPServiceImpl
import org.koin.dsl.module

val serviceModule = module {
    single<OTPService> { OTPServiceImpl(get()) }
}