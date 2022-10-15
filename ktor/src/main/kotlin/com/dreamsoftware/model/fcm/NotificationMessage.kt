package com.dreamsoftware.model.fcm

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationMessage(
    @SerialName("message_id")
    val messageId: String?,
    val error: String?
)
