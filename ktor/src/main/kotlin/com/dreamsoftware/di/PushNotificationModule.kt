package com.dreamsoftware.di

import com.dreamsoftware.service.OTPSender
import com.dreamsoftware.service.impl.OTPPushSenderServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val PUSH_NOTIFICATION_SENDER = "pushNotificationSender"

val pushNotificationModule = module {
    single<OTPSender>(named(PUSH_NOTIFICATION_SENDER)) { OTPPushSenderServiceImpl() }
}