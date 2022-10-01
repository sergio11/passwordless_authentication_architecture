package com.dreamsoftware.di

import com.dreamsoftware.model.AppConfig
import org.koin.dsl.module
import redis.clients.jedis.HostAndPort
import redis.clients.jedis.JedisCluster

val redisModule = module {
    single { (appConfig : AppConfig) ->
        JedisCluster(hashSetOf(*appConfig.redis.nodes.map {
            HostAndPort(it.host, it.port) }.toTypedArray()) )
    }
}