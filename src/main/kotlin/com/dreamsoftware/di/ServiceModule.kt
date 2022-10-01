package com.dreamsoftware.di

import com.dreamsoftware.service.OTPGenerator
import com.dreamsoftware.service.OTPService
import com.dreamsoftware.service.impl.OTPGeneratorImpl
import com.dreamsoftware.service.impl.OTPServiceImpl
import org.koin.dsl.module

val serviceModule = module {
    single<OTPGenerator> { OTPGeneratorImpl(get()) }
    single<OTPService> { OTPServiceImpl(get(), get()) }
}