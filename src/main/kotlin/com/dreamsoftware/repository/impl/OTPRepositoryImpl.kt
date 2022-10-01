package com.dreamsoftware.repository.impl

import com.dreamsoftware.repository.OTPRepository
import redis.clients.jedis.JedisCluster

class OTPRepositoryImpl(
    private val jedisCluster: JedisCluster
): OTPRepository {

}