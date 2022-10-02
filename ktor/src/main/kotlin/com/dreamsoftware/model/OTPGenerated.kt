package com.dreamsoftware.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OTPGenerated(
    @SerialName("operation_id")
    val operationId: String,
    @SerialName("otp")
    val otp: String,
    @SerialName("expire_time")
    val expireTime: Long,
    @SerialName("destination")
    val destination: String
)
