package com.dreamsoftware.service.impl

import com.dreamsoftware.model.OtpSenderConfig
import com.dreamsoftware.model.SmsSenderConfig
import com.dreamsoftware.model.exception.OTPSenderFailedException
import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber

class OTPSmsSenderServiceImpl: SupportOTPSender<SmsSenderConfig>() {

    override suspend fun sendOTP(
        otpSetting: SmsSenderConfig,
        otp: String,
        destination: String,
        properties: Map<String, String>
    ) {
        println("OTPSmsSenderServiceImpl - sendOTP CALLED!")
        with(otpSetting) {
            Twilio.init(accountSid, serviceKey)
            try {
                val message = Message.creator(
                    PhoneNumber(destination),
                    PhoneNumber(fromPhoneNumber),
                    createMessage(otpSetting, otp, destination, properties)
                ).create()
                println("OTPSmsSenderServiceImpl - message.errorMessage ${message.errorMessage} CALLED!")
                println("OTPSmsSenderServiceImpl - message.errorCode ${message.errorCode} CALLED!")
                println("OTPSmsSenderServiceImpl - message.status ${message.status} CALLED!")
            } catch(ex: Exception) {
                ex.printStackTrace()
                throw OTPSenderFailedException("An error occurred when sending a SMS", ex)
            }
        }
    }

    override fun createMessage(
        otpSetting: OtpSenderConfig,
        otp: String,
        destination: String,
        properties: Map<String, String>
    ): String =  "${otpSetting.messageTitle} ${super.createMessage(otpSetting, otp, destination, properties)}"
}