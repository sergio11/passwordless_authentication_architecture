package com.dreamsoftware.repository.impl

import com.dreamsoftware.model.OTPGenerated
import com.dreamsoftware.model.exception.OTPNotFoundException
import com.dreamsoftware.model.exception.OTPSaveDataException
import com.dreamsoftware.repository.OTPRepository
import redis.clients.jedis.JedisCluster
import redis.clients.jedis.args.ExpiryOption

class OTPRepositoryImpl(
    private val jedisCluster: JedisCluster
): OTPRepository {

    override fun save(otpGenerated: OTPGenerated) {
        runCatching {
            with(jedisCluster) {
                with(otpGenerated) {
                    jsonSet(operationId, this)
                    // Set expiry only when the key has no expiry
                    expire(operationId, expireTime, ExpiryOption.NX)
                }
            }
        }.getOrElse {
            throw OTPSaveDataException("An error occurred when trying to save OTP data", it)
        }
    }

    override fun findByDestination(destination: String): OTPGenerated = runCatching {
        jedisCluster.jsonGet(destination) as OTPGenerated
    }.getOrElse {
        throw OTPNotFoundException("OTP data not found", it)
    }

    override fun existsByOperationIdAndOtp(operationId: String, otp: String): Boolean = runCatching {
        val otpGenerated = jedisCluster.jsonGet(operationId) as OTPGenerated
        otpGenerated.otp == otp
    }.getOrDefault(false)

}