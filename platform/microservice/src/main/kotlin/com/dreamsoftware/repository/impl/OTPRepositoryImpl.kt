package com.dreamsoftware.repository.impl

import com.dreamsoftware.model.OTPGenerated
import com.dreamsoftware.model.exception.OTPNotFoundException
import com.dreamsoftware.model.exception.OTPSaveDataException
import com.dreamsoftware.repository.OTPRepository
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
    }

    override fun save(otpGenerated: OTPGenerated) {
        runCatching {
            with(jedisCluster) {
                with(otpGenerated) {
                    val itemKey = OTP_INDEX_PREFIX + operationId
                    jsonSet(itemKey, toJSON())
                    expire(itemKey, ttlInSeconds)
                }
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
    }

    override fun findByOperationId(operationId: String): OTPGenerated = runCatching {
        println("findByOperationId -> ${OTP_INDEX_PREFIX + operationId}")
        jedisCluster.jsonGet(OTP_INDEX_PREFIX + operationId, OTPGenerated::class.java)
    }.getOrElse {
        throw OTPNotFoundException("OTP data not found", it)
    }

    override fun existsByOperationIdAndOtp(operationId: String, otp: String): Boolean = runCatching {
        findByOperationId(operationId).otp == otp
    }.getOrDefault(false)

    override fun deleteByOperationId(operationId: String) {
        jedisCluster.del(OTP_INDEX_PREFIX + operationId).also {
            println("deleteByOperationId -> $operationId result -> $it")
        }
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