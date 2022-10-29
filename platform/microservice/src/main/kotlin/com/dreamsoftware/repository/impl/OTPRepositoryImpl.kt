package com.dreamsoftware.repository.impl

import com.dreamsoftware.model.OTPGenerated
import com.dreamsoftware.model.RedisStorageConfig
import com.dreamsoftware.model.exception.OTPDestinationIsBlockedException
import com.dreamsoftware.model.exception.OTPNotFoundException
import com.dreamsoftware.model.exception.OTPSaveDataException
import com.dreamsoftware.repository.OTPRepository
import com.dreamsoftware.utils.hashSha256andEncode
import redis.clients.jedis.JedisCluster

class OTPRepositoryImpl(
    private val jedisCluster: JedisCluster,
    private val redisStorageConfig: RedisStorageConfig
): OTPRepository {

    override fun save(otpGenerated: OTPGenerated): Unit = with(otpGenerated) {
        checkDestinationIsBlocked(destination)
        runCatching {
            with(jedisCluster) {
                with(redisStorageConfig) {
                    val operationKey = operationsPrefix + operationId
                    jsonSet(operationKey, toJSON())
                    expire(operationKey, ttlInSeconds)
                    set(destinationsPrefix + destination.hashSha256andEncode(), operationId)
                }
            }
        }.getOrElse {
            throw OTPSaveDataException("An error occurred when trying to save OTP data", it)
        }
    }

    override fun findByDestination(destination: String): OTPGenerated = runCatching {
        val operationId = jedisCluster.get(redisStorageConfig.destinationsPrefix + destination.hashSha256andEncode())
        findByOperationId(operationId)
    }.getOrElse {
        throw OTPNotFoundException("OTP data not found", it)
    }

    override fun findByOperationId(operationId: String): OTPGenerated = runCatching {
        println("findByOperationId -> ${redisStorageConfig.operationsPrefix + operationId}")
        jedisCluster.jsonGet(redisStorageConfig.operationsPrefix + operationId, OTPGenerated::class.java)
    }.getOrElse {
        throw OTPNotFoundException("OTP data not found", it)
    }.also {
        checkDestinationIsBlocked(it.destination)
    }

    override fun existsByOperationIdAndOtp(operationId: String, otp: String): Boolean = runCatching {
        findByOperationId(operationId)
    }.getOrElse {
        throw OTPNotFoundException("OTP data not found", it)
    }.also {
        checkDestinationIsBlocked(it.destination)
    }.let {
        val isValid = it.otp == otp
        if(!isValid) {
            recordVerificationFailed(destination = it.destination)
        }
        isValid
    }

    override fun deleteByOperationId(operationId: String) {
        jedisCluster.del(redisStorageConfig.operationsPrefix + operationId).also {
            println("deleteByOperationId -> $operationId result -> $it")
        }
    }

    private fun checkDestinationIsBlocked(destination: String) = with(jedisCluster) {
        with(redisStorageConfig) {
            destination.hashSha256andEncode().let { faultsPrefix + it }.let { key ->
                if(exists(key) && get(key).toInt() > maxVerificationFailedByDestination) {
                    throw OTPDestinationIsBlockedException("$destination has been blocked.")
                }
            }
        }
    }

    private fun recordVerificationFailed(destination: String) = runCatching {
        with(jedisCluster) {
            with(redisStorageConfig) {
                destination.hashSha256andEncode().let { key ->
                    incr(faultsPrefix + key)
                    expire(key, verificationFailedTtlInSeconds)
                }
            }
        }
    }.getOrElse {
        throw OTPSaveDataException("An error occurred when recording verification failed")
    }

}