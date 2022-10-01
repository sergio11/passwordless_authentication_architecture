package com.dreamsoftware.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OTPGenerationRequestDTO(
    @SerialName("user_id")
    val userId: String,
    @SerialName("phone_number")
    val phoneNumber: String
)
