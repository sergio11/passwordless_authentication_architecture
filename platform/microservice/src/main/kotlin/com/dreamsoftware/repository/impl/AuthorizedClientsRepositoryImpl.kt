package com.dreamsoftware.repository.impl

import com.dreamsoftware.model.RedisStorageConfig
import com.dreamsoftware.model.exception.AuthorizedClientsCheckException
import com.dreamsoftware.model.exception.AuthorizedClientsRemoveException
import com.dreamsoftware.model.exception.AuthorizedClientsSaveException
import com.dreamsoftware.repository.AuthorizedClientsRepository
import com.dreamsoftware.utils.hashSha256andEncode
import redis.clients.jedis.JedisCluster
import java.util.*

class AuthorizedClientsRepositoryImpl(
    private val jedisCluster: JedisCluster,
    private val redisStorageConfig: RedisStorageConfig
): AuthorizedClientsRepository {

    override fun save(name: String, password: String): String = runCatching {
        "${UUID.randomUUID()}_${name}_${password}".hashSha256andEncode().also {
            jedisCluster.lpush(redisStorageConfig.authorizedClientsKey, it)
        }
    }.getOrElse {
        throw AuthorizedClientsSaveException("An error occurred when trying to save client")
    }

    override fun delete(id: String) {
        runCatching {
            jedisCluster.lrem(redisStorageConfig.authorizedClientsKey, 0, id)
        }.getOrElse {
            throw AuthorizedClientsRemoveException("An error occurred when trying to remove client")
        }
    }

    override fun exists(id: String): Boolean = runCatching {
        jedisCluster.lpos(redisStorageConfig.authorizedClientsKey,  id) != null
    }.getOrElse {
        throw AuthorizedClientsCheckException("An error occurred when checking clients")
    }

}