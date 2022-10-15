package com.dreamsoftware.di

import com.dreamsoftware.rest.dto.OTPTypeEnum
import com.dreamsoftware.service.OTPGenerator
import com.dreamsoftware.service.OTPSender
import com.dreamsoftware.service.OTPService
import com.dreamsoftware.service.impl.OTPGeneratorImpl
import com.dreamsoftware.service.impl.OTPMailSenderServiceImpl
import com.dreamsoftware.service.impl.OTPServiceImpl
import com.dreamsoftware.service.impl.OTPSmsSenderServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val serviceModule = module {
    includes(pushNotificationModule)
    single<OTPGenerator> { OTPGeneratorImpl() }
    single<OTPService> { OTPServiceImpl(get(), get(), get()) }
    factory<OTPSender> { (type: OTPTypeEnum) ->
        when(type) {
            OTPTypeEnum.SMS -> OTPSmsSenderServiceImpl()
            OTPTypeEnum.PUSH -> get(named(PUSH_NOTIFICATION_SENDER))
            OTPTypeEnum.MAIL -> OTPMailSenderServiceImpl()
        }
    }
}