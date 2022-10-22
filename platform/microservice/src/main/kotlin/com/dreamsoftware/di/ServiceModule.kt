package com.dreamsoftware.di

import com.dreamsoftware.service.AuthorizedClientsService
import com.dreamsoftware.service.OTPGenerator
import com.dreamsoftware.service.OTPService
import com.dreamsoftware.service.impl.AuthorizedClientsServiceImpl
import com.dreamsoftware.service.impl.OTPGeneratorImpl
import com.dreamsoftware.service.impl.OTPServiceImpl
import org.koin.dsl.module

val serviceModule = module {
    includes(pushNotificationModule, mailModule, smsNotificationModule)
    single<OTPGenerator> { OTPGeneratorImpl() }
    single<OTPService> { OTPServiceImpl(get(), get(), get()) }
    single<AuthorizedClientsService> { AuthorizedClientsServiceImpl(get()) }
}