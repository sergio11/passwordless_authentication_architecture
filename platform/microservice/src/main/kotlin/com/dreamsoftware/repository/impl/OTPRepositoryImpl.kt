package com.dreamsoftware.repository.impl

import com.dreamsoftware.model.OTPGenerated
import com.dreamsoftware.model.exception.OTPDestinationIsBlockedException
import com.dreamsoftware.model.exception.OTPNotFoundException
import com.dreamsoftware.model.exception.OTPSaveDataException
import com.dreamsoftware.repository.OTPRepository
import com.dreamsoftware.utils.hashSha256andEncode
import redis.clients.jedis.JedisCluster
import redis.clients.jedis.search.IndexDefinition
import redis.clients.jedis.search.IndexOptions
import redis.clients.jedis.search.Schema

class OTPRepositoryImpl(
    private val jedisCluster: JedisCluster
): OTPRepository {

    init {
        createRSearchIndex()
    }

    private companion object {
        private const val OTP_INDEX_NAME = "mfa-otp-index"
        private const val OTP_INDEX_PREFIX = "mfa:"
        private const val VERIFICATION_FAILED_TTL_IN_SECONDS = 15 * 60L
        private const val MAX_VERIFICATION_FAILED_BY_DESTINATION = 5
    }

    override fun save(otpGenerated: OTPGenerated): Unit = with(otpGenerated) {
        checkDestinationIsBlocked(destination)
        runCatching {
            with(jedisCluster) {
                val itemKey = OTP_INDEX_PREFIX + operationId
                jsonSet(itemKey, toJSON())
                expire(itemKey, ttlInSeconds)
            }
        }.getOrElse {
            throw OTPSaveDataException("An error occurred when trying to save OTP data", it)
        }
    }

    override fun findByDestination(destination: String): OTPGenerated = runCatching {
        jedisCluster.jsonGet(destination).let {
            it as OTPGenerated
        }
    }.getOrElse {
        throw OTPNotFoundException("OTP data not found", it)
    }.also {
        checkDestinationIsBlocked(it.destination)
    }

    override fun findByOperationId(operationId: String): OTPGenerated = runCatching {
        println("findByOperationId -> ${OTP_INDEX_PREFIX + operationId}")
        jedisCluster.jsonGet(OTP_INDEX_PREFIX + operationId, OTPGenerated::class.java)
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
        jedisCluster.del(OTP_INDEX_PREFIX + operationId).also {
            println("deleteByOperationId -> $operationId result -> $it")
        }
    }

    private fun checkDestinationIsBlocked(destination: String) = with(jedisCluster) {
        destination.hashSha256andEncode().let { key ->
            if(exists(key) && get(key).toInt() > MAX_VERIFICATION_FAILED_BY_DESTINATION) {
                throw OTPDestinationIsBlockedException("$destination has been blocked.")
            }
        }
    }

    private fun recordVerificationFailed(destination: String) = runCatching {
        with(jedisCluster) {
            destination.hashSha256andEncode().let { key ->
                incr(key)
                expire(key, VERIFICATION_FAILED_TTL_IN_SECONDS)
            }
        }
    }.getOrElse {
        throw OTPSaveDataException("An error occurred when recording verification failed")
    }

    private fun createRSearchIndex() {
        try {
            val options = jedisCluster.ftInfo(OTP_INDEX_NAME)
            options.forEach { (k, v) ->
                println("Key: $k, Value: $v")
            }
        } catch (e: Exception) {
            println("createRSearchIndex ex -> ${e.message}")
            jedisCluster.ftCreate(OTP_INDEX_NAME, IndexOptions
                .defaultOptions()
                .setDefinition(
                    IndexDefinition()
                        .setPrefixes(OTP_INDEX_PREFIX)
                ), Schema()
                .addTextField("operation_id", 5.0)
                .addTextField("otp", 1.0)
                .addTextField("destination", 1.0));
        }
    }

}