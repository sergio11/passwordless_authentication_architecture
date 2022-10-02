package com.dreamsoftware.repository.impl

import com.dreamsoftware.model.OTPGenerated
import com.dreamsoftware.model.exception.OTPNotFoundException
import com.dreamsoftware.model.exception.OTPSaveDataException
import com.dreamsoftware.repository.OTPRepository
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import redis.clients.jedis.JedisCluster
import redis.clients.jedis.args.ExpiryOption

class OTPRepositoryImpl(
    private val jedisCluster: JedisCluster
): OTPRepository {

    private val json by lazy {
        Json {
            prettyPrint = true
            isLenient = true
        }
    }

    override fun save(otpGenerated: OTPGenerated) {
        runCatching {
            with(jedisCluster) {
                with(otpGenerated) {
                    jsonSet(operationId, json.encodeToString(this))
                    // Set expiry only when the key has no expiry
                    expire(operationId, expireTime, ExpiryOption.NX)
                }
            }
        }.getOrElse {
            throw OTPSaveDataException("An error occurred when trying to save OTP data", it)
        }
    }

    override fun findByDestination(destination: String): OTPGenerated = runCatching {
        jedisCluster.jsonGet(destination).let {
            json.decodeFromString<OTPGenerated>(it as String)
        }
    }.getOrElse {
        throw OTPNotFoundException("OTP data not found", it)
    }

    override fun existsByOperationIdAndOtp(operationId: String, otp: String): Boolean = runCatching {
        val otpGenerated = jedisCluster.jsonGet(operationId).let {
            json.decodeFromString<OTPGenerated>(it as String)
        }
        otpGenerated.otp == otp
    }.getOrDefault(false)

}