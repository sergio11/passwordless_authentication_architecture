package com.dreamsoftware.repository.impl

import com.dreamsoftware.model.OTPGenerated
import com.dreamsoftware.model.exception.OTPNotFoundException
import com.dreamsoftware.model.exception.SaveOTPDataException
import com.dreamsoftware.repository.OTPRepository
import redis.clients.jedis.JedisCluster

class OTPRepositoryImpl(
    private val jedisCluster: JedisCluster
): OTPRepository {

    override fun save(userId: String, otpGenerated: OTPGenerated) {
        runCatching {
            jedisCluster.jsonSet(userId, otpGenerated)
        }.getOrElse {
            throw SaveOTPDataException("An error occurred when trying to save OTP data", it)
        }
    }

    override fun findNotExpiredByUserId(userId: String): OTPGenerated = runCatching {
        jedisCluster.jsonGet(userId) as OTPGenerated
    }.getOrElse {
        throw OTPNotFoundException("OTP data not found", it)
    }

}