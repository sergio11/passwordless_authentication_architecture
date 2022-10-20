package com.dreamsoftware.service.impl

import com.dreamsoftware.model.PushNotificationSenderConfig
import com.dreamsoftware.model.fcm.NotificationData
import com.dreamsoftware.model.fcm.NotificationPushPriority
import com.dreamsoftware.model.fcm.NotificationRequestData
import com.dreamsoftware.model.fcm.NotificationResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class OTPPushSenderServiceImpl : SupportOTPSender<PushNotificationSenderConfig>() {

    override suspend fun sendOTP(
        otpSetting: PushNotificationSenderConfig,
        otp: String,
        destination: String,
        properties: Map<String, String>
    ) {
        println("OTPPushSenderServiceImpl - sendOTP CALLED!")
        with(otpSetting) {
            buildHttpClient(serviceKey).use {
                it.post(serviceUrl) {
                    contentType(ContentType.Application.Json)
                    setBody(
                        NotificationRequestData(
                            data = hashMapOf(Pair("OTP", otp)),
                            registrationTokenList = listOf(destination),
                            notification = NotificationData(
                                title = messageTitle,
                                body = createMessage(otpSetting, otp, destination, properties)
                            ),
                            priority = NotificationPushPriority.HIGH.value
                        )
                    )
                }.body<NotificationResponse>().also { response ->
                    println("OTPPushSenderServiceImpl - response.success: ${response.success} CALLED!")
                    println("OTPPushSenderServiceImpl - response.failure: ${response.failure} CALLED!")
                    println("OTPPushSenderServiceImpl - response.multicastId: ${response.multicastId} CALLED!")
                    println("OTPPushSenderServiceImpl - response.results: ${response.results} CALLED!")
                    if(response.failure > 0) {
                        throw OTPSenderFailedException("OTP by Push Notification failed")
                    }
                }
            }
        }
    }

    private fun buildHttpClient(serviceKey: String) = HttpClient(Apache) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        install(DefaultRequest) {
            headers.append("Authorization", "key=$serviceKey")
        }
        install(Logging) {
            logger = Logger.DEFAULT
            this.level = LogLevel.ALL
        }
    }
}