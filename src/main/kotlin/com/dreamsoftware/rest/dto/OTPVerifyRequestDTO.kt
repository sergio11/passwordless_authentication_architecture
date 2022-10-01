package com.dreamsoftware.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OTPVerifyRequestDTO(
    @SerialName("user_id")
    val userId: String,
    val otp: String
)
