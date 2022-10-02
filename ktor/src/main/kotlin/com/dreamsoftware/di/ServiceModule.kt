package com.dreamsoftware.di

import com.dreamsoftware.rest.dto.OTPTypeEnum
import com.dreamsoftware.service.OTPGenerator
import com.dreamsoftware.service.OTPSender
import com.dreamsoftware.service.OTPService
import com.dreamsoftware.service.impl.*
import org.koin.dsl.module

val serviceModule = module {
    single<OTPGenerator> { OTPGeneratorImpl() }
    single<OTPService> { OTPServiceImpl(get(), get(), get()) }
    factory<OTPSender> { (type: OTPTypeEnum) ->
        when(type) {
            OTPTypeEnum.SMS -> OTPSmsSenderServiceImpl()
            OTPTypeEnum.PUSH -> OTPPushSenderServiceImpl()
            OTPTypeEnum.MAIL -> OTPMailSenderServiceImpl()
        }
    }
}