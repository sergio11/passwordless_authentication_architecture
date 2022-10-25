package com.dreamsoftware.utils

import net.schmizz.sshj.SSHClient

fun SSHClient.connect(hostname: String, port: Int, retryNumber: Int) {
    var retryCount = 0
    var isSuccess = false
    do {
        isSuccess = runCatching {
            connect(hostname, port)
            true
        }.getOrElse {
            retryCount++
            false
        }
    } while (!isSuccess && retryCount < retryNumber)
    if(!isSuccess) {
        throw RuntimeException("Couldn't connect to SFTP server")
    }
}