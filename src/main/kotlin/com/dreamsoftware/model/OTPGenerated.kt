package com.dreamsoftware.model

import java.util.*

data class OTPGenerated(
    val operationId: UUID,
    val otp: String,
    val expireTime: Long,
    val destination: String
)
