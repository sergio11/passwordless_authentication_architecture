package com.dreamsoftware.model.fcm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(
    @SerialName("multicast_id")
    val multicastId: Long,
    val success: Int,
    val failure: Int,
    @SerialName("canonical_ids")
    val canonicalIdList: Int,
    val results: List<NotificationMessage>
)