package com.dreamsoftware.utils

import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest
import java.util.*

fun String.hashSha256andEncode() =
    Base64.getEncoder().encodeToString(
        MessageDigest.getInstance("SHA-256")
        .digest(toByteArray()))

fun String.md5Digest(): ByteArray = MessageDigest.getInstance("MD5").digest(toByteArray(UTF_8))
