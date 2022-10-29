package com.dreamsoftware.model

data class AppConfig(val senders: SendersConfig, val auth: AuthConfig, val redis: RedisClusterConfig)
data class SendersConfig(val mailSender: MailSenderConfig, val smsSender: SmsSenderConfig, val pushSender: PushNotificationSenderConfig)
data class AuthConfig(val realmName: String, val adminUser: String, val password: String)
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
    val serviceKey: String,
    val emailFrom: String,
    val emailFromName: String,
    val messageTemplateId: String,
    val messageTitlePlaceholder: String,
    val messageContentPlaceholder: String
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
data class RedisClusterConfig(val storageConfig: RedisStorageConfig, val nodes: List<RedisNodeConfig>)
data class RedisStorageConfig(
    val operationsPrefix: String,
    val destinationsPrefix: String,
    val faultsPrefix: String,
    val verificationFailedTtlInSeconds: Long,
    val maxVerificationFailedByDestination: Int,
    val authorizedClientsKey: String)
data class RedisNodeConfig(val host: String, val port: Int)