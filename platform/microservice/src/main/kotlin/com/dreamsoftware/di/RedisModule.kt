package com.dreamsoftware.di

import com.dreamsoftware.model.RedisClusterConfig
import org.koin.dsl.module
import redis.clients.jedis.HostAndPort
import redis.clients.jedis.JedisCluster

val redisModule = module {
    single {
        JedisCluster(hashSetOf(*get<RedisClusterConfig>().nodes.map {
            HostAndPort(it.host, it.port) }.toTypedArray()) )
    }
}