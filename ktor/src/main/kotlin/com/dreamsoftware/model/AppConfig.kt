package com.dreamsoftware.model

data class AppConfig(val mfa: MfaConfig, val redis: RedisClusterConfig)
data class MfaConfig(val mailSender: MailSenderConfig, val smsSender: SmsSenderConfig, val pushSender: PushNotificationSenderConfig)
interface OtpSenderConfig {
    val messageTitle: String
    val messageTemplate: String
    val otpLength: Int
    val useLetters: Boolean
    val useDigits: Boolean
    val ttlMinutes: Int
}
data class MailSenderConfig(
    override val messageTitle: String,
    override val messageTemplate: String,
    override val otpLength: Int,
    override val useLetters: Boolean,
    override val useDigits: Boolean,
    override val ttlMinutes: Int,
    val serviceKey: String
): OtpSenderConfig
data class PushNotificationSenderConfig(
    override val messageTitle: String,
    override val messageTemplate: String,
    override val otpLength: Int,
    override val useLetters: Boolean,
    override val useDigits: Boolean,
    override val ttlMinutes: Int,
    val serviceKey: String,
    val serviceUrl: String
): OtpSenderConfig
data class SmsSenderConfig(
    override val messageTitle: String,
    override val messageTemplate: String,
    override val otpLength: Int,
    override val useLetters: Boolean,
    override val useDigits: Boolean,
    override val ttlMinutes: Int,
    val accountSid: String,
    val serviceKey: String,
    val fromPhoneNumber: String
): OtpSenderConfig
data class RedisClusterConfig(val nodes: List<RedisNodeConfig>)
data class RedisNodeConfig(val host: String, val port: Int)