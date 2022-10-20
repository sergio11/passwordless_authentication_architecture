package com.dreamsoftware.repository.impl

import com.dreamsoftware.model.exception.AuthorizedClientsCheckException
import com.dreamsoftware.model.exception.AuthorizedClientsRemoveException
import com.dreamsoftware.model.exception.AuthorizedClientsSaveException
import com.dreamsoftware.repository.AuthorizedClientsRepository
import com.dreamsoftware.utils.hashSha256andEncode
import redis.clients.jedis.JedisCluster
import java.util.*

class AuthorizedClientsRepositoryImpl(
    private val jedisCluster: JedisCluster
): AuthorizedClientsRepository {

    private companion object {
        const val AUTHORIZED_CLIENTS_KEY = "mfa_authorized_clients"
    }

    override fun save(name: String, password: String): String = runCatching {
        "${UUID.randomUUID()}_${name}_${password}".hashSha256andEncode().also {
            jedisCluster.lpush(AUTHORIZED_CLIENTS_KEY, it)
        }
    }.getOrElse {
        throw AuthorizedClientsSaveException("An error occurred when trying to save client")
    }

    override fun delete(id: String) {
        runCatching {
            jedisCluster.lrem(AUTHORIZED_CLIENTS_KEY, 0, id)
        }.getOrElse {
            throw AuthorizedClientsRemoveException("An error occurred when trying to remove client")
        }
    }

    override fun exists(id: String): Boolean = runCatching {
        jedisCluster.lpos(AUTHORIZED_CLIENTS_KEY,  id) != null
    }.getOrElse {
        throw AuthorizedClientsCheckException("An error occurred when checking clients")
    }

}