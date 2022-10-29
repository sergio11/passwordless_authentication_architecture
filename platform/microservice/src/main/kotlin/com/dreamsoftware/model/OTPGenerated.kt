package com.dreamsoftware.model

import com.dreamsoftware.rest.dto.OTPTypeEnum
import com.dreamsoftware.utils.JSONConvertible
import com.google.gson.annotations.SerializedName

data class OTPGenerated(
    @SerializedName("operation_id")
    val operationId: String,
    @SerializedName("otp")
    val otp: String,
    @SerializedName("expire_at_millis")
    val expireAtInMillis: Long,
    @SerializedName("ttl_in_seconds")
    val ttlInSeconds: Long,
    @SerializedName("destination")
    val destination: String,
    @SerializedName("destination_hash")
    val destinationHash: String,
    @SerializedName("sender_type")
    val senderType: OTPTypeEnum,
    @SerializedName("properties")
    val properties: Map<String, String>,
    @SerializedName("attempts")
    val attempts: Int
): JSONConvertible
