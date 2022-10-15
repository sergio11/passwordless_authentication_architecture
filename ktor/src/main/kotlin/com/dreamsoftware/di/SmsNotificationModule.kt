package com.dreamsoftware.di

import com.dreamsoftware.model.SmsSenderConfig
import com.dreamsoftware.service.OTPSender
import com.dreamsoftware.service.impl.OTPSmsSenderServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val SMS_NOTIFICATION_SENDER = "smsNotificationSender"

val smsNotificationModule = module {
    single<OTPSender<SmsSenderConfig>>(named(SMS_NOTIFICATION_SENDER)) { OTPSmsSenderServiceImpl() }
}