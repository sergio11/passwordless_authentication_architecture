package com.dreamsoftware.model

data class AppConfig(val redis: RedisClusterConfig)
data class RedisClusterConfig(val nodes: List<RedisNodeConfig>)
data class RedisNodeConfig(val host: String, val port: Int)