package com.dreamsoftware.model

data class OTPGenerated(
    val operationId: String,
    val otp: String,
    val expireTime: Long,
    val destination: String
)
