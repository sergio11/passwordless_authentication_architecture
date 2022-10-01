package com.dreamsoftware.rest.dto

import kotlinx.serialization.Serializable

@Serializable
data class OTPVerifyResultDTO(
    val verified: Boolean,
    val destination: String
)
