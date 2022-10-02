package com.dreamsoftware.model

data class AppConfig(val mfa: MfaConfig, val redis: RedisClusterConfig)
data class MfaConfig(val senders: List<OtpSenderConfig>)
data class OtpSenderConfig(
    val type: String,
    val name: String,
    val messageTitle: String,
    val messageTemplate: String,
    val otpLength: Int,
    val useLetters: Boolean,
    val useDigits: Boolean,
    val ttlMinutes: Int,
    val serviceKey: String)
data class RedisClusterConfig(val nodes: List<RedisNodeConfig>)
data class RedisNodeConfig(val host: String, val port: Int)