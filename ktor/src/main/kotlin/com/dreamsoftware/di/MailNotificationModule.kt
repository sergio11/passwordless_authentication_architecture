package com.dreamsoftware.di

import com.dreamsoftware.model.MailSenderConfig
import com.dreamsoftware.service.OTPSender
import com.dreamsoftware.service.impl.OTPMailSenderServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MAIL_NOTIFICATION_SENDER = "mailNotificationSender"

val mailModule = module {
    single<OTPSender<MailSenderConfig>>(named(MAIL_NOTIFICATION_SENDER)) { OTPMailSenderServiceImpl() }
}